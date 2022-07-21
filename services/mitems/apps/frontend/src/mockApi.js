import { Server, Model, hasMany, belongsTo, Serializer } from "miragejs";

import slug from 'slug';

const slug = (...elements) => {
    return elements.map(element => slug(element, "-")).join(".").toLowerCase();
}

const createElement = (schema, flowTitle, itemName, element) => {
    if (!element.id) {
        element.slug = slug([flowTitle, itemName, element.description]);
    }
    return schema.elements.create(element)
};

new Server({
    models: {
        flow: Model.extend({
            items: hasMany()
        }),
        item: Model.extend({
            flow: belongsTo(),
            elements: hasMany()
        }),
        element: Model.extend({
            item: belongsTo()
        })
    },
    serializers: {
        flow: Serializer.extend({
            include: ["items"]
        }),
        item: Serializer.extend({
            include: ["flow", "elements"]
        })
    },
    routes() {
        this.namespace = "/api";

        // Import data
        this.get("/import-data");

        // Flows
        this.get("/flows");
        this.get("/flows/:id");
        this.post("/flows", function (schema, request) {
            const body = JSON.parse(request.requestBody);
            return schema.flows.create({
                title: body.title,
                description: body.description
            });
        });
        this.patch("/flows/:id", function (schema, request) {
            const id = request.params.id;
            const body = JSON.parse(request.requestBody);
            return schema.flows.find(id).update({
                title: body.title,
                description: body.description
            });
        });
        this.del("/flows/:id");

        // Items
        this.get("/items/:id");
        this.post("/items/:flowId/create", function (schema, request) {
            const flowId = request.params.flowId;
            const flow = schema.flows.find(flowId);
            const body = JSON.parse(request.requestBody);
            return flow.createItem({
                ...body,
                slug: slug(flow.title, body.name),
                elements: body.elements.map(element => createElement(schema, flow.title, body.name, element))
            });
        });
        this.patch("/items/:id", function (schema, request) {
            const id = request.params.id;
            const body = JSON.parse(request.requestBody);
            const item = schema.items.find(id);
            item.elements = body.elements.map(element => createElement(schema, item.flow.title, body.name, element));
            item.name = body.name;
            item.save();
            return item;
        });
        this.del("/items/:id");
    },
    seeds(server) {
        server.create("flow", {
            title: "Samoprocjena",
            description:
                "Flow koji vodi korisnika kroz samorpocjenu rizika od koronavirusa",
            items: [
                server.create("item", {
                    name: "Uvodna poruka",
                    slug: slug("Samoprocjena", "Uvodna poruka"),
                    elements: [
                        server.create("element", {
                            description: "Tekst poruke",
                            type: "whatsapp",
                            content: "*Ovo ne traje dugo, vaše stanje možemo procijeniti u manje od dvije minute 😊*\n\nProći ćemo kroz nekoliko pitanja i na kraju ću vam dati preporuku u skladu s najnovijim smjernicama. U slučaju da ćete imati bilo kakve dvojbe, kontaktirajte svog liječnika obiteljske medicine ili nazovite na broj 113.\n\n➡️ Jeste li spremni?",
                            slug: slug("Samoprocjena", "Uvodna poruka", "Tekst poruke")
                        }),
                        server.create("element", {
                            description: "Opcije",
                            type: "menu",
                            content: `[
                                {
                                  "id": "continue",
                                  "text": "Da, krenimo"
                                },
                                {
                                  "id": "menu",
                                  "text": "Odustajem"
                                }
                              ]`,
                            slug: slug("Samoprocjena", "Uvodna poruka", "Opcije")
                        })
                    ]
                }),
                server.create("item", {
                    name: "Rezultat: hitna",
                    slug: slug("Samoprocjena", "Rezultat: hitna"),
                    elements: [
                        server.create("element", {
                            description: "Tekst poruke",
                            type: "whatsapp",
                            content: "*❗ Ovo su jako ozbiljni simptomi. Trebali biste nazvati Hitnu pomoć na broj 194. Zamolite i neku blisku osobu da vam pomogne.*\n\nStručnjaci u Hitnoj spremni su za ovakve situacije, bit ćete u dobrim rukama. Oni će vas voditi dalje kroz postupak.",
                            slug: slug("Samoprocjena", "Rezultat: hitna", "Tekst poruke")
                        }),
                        server.create("element", {
                            description: "Opcije",
                            type: "menu",
                            content: `[
                                {
                                  "id": "start_assessment",
                                  "text": "Pokrenimo novu procjenu 👨‍👩‍👧"
                                },
                                {
                                  "id": "menu",
                                  "text": "Pokaži mi ostale opcije 🔠"
                                }
                              ]`,
                            slug: slug("Samoprocjena", "Rezultat: hitna", "Opcije")
                        })
                    ]
                })
            ]
        });
        server.create("flow", {
            title: "Dojava stanja u kućanstvu",
            description:
                "Flow koji vodi korisnika kroz dojavu stanja, slanja lokacije itd.",
            items: [
                server.create("item", {
                    name: "Zahvala na kraju",
                    slug: slug("Dojava stanja u kućanstvu", "Zahvala na kraju"),
                    elements: [
                        server.create("element", {
                            description: "Poslana lokacija",
                            type: "whatsapp",
                            content: "*Puno vam hvala 🙏🏻 Ovo je bila vaša {{ reportNumber }}. dojava, a {{ levelText }}\n\nSvaki dan šaljite informacije o stanju u svom domu i ne zaboravite - vaš je doprinos itekako bitan.\n\n💡 Pošaljite *X* za nastavak razgovora.",
                            slug: slug("Dojava stanja u kućanstvu", "Zahvala na kraju", "Poslana lokacija")
                        }),
                        server.create("element", {
                            description: "Nije poslana lokacija",
                            type: "whatsapp",
                            content: "*Hvala vam 🙋🏻‍♂️ Puno ste pomogli i bez slanja svoje lokacije. Ovo je bila vaša {{ reportNumber }}. dojava, a {{ levelText }}\n\nSvaki dan šaljite informacije o stanju u svom domu i ne zaboravite - vaš je doprinos itekako bitan.\n\n💡 Pošaljite *X* za nastavak razgovora.",
                            slug: slug("Dojava stanja u kućanstvu", "Zahvala na kraju", "Nije poslana lokacija")
                        }),
                        server.create("element", {
                            description: "Trenutni level",
                            type: "whatsapp",
                            content: "vaš trenutni status je {{ currentStatus }}*\n\n",
                            slug: slug("Dojava stanja u kućanstvu", "Zahvala na kraju", "Trenutni level")
                        }),
                        server.create("element", {
                            description: "Sljedeći level",
                            type: "whatsapp",
                            content: "Sa {{ nextLevel }} {{'dojava'|pluralize(nextLevel, 'I')}} idete na sljedeću razinu.",
                            slug: slug("Dojava stanja u kućanstvu", "Zahvala na kraju", "Sljedeći level")
                        }),
                        server.create("element", {
                            description: "Leveli",
                            type: "json",
                            content: `{
                                "1": "BEBA ŠTAMPAR 👶🏻",
                                "3": "ŠTAMPAR ŠKOLARAC 👦🏻",
                                "7": "ŠTAMPAR STUDENT MEDICINE 👨🏻‍🎓",
                                "14": "DOKTOR ŠTAMPAR 👨🏻‍⚕️",
                                "30": "PROFESOR DOKTOR ŠTAMPAR 👴🏻",
                                "60": "OTAC PREVENTIVE 🎩"
                              }`,
                            slug: slug("Dojava stanja u kućanstvu", "Zahvala na kraju", "Leveli")
                        })
                    ]
                })
            ]
        });
    }
});
