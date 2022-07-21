from operator import itemgetter

from django.db import models
from slugify import slugify

from elements.models import Element
from flows.models import Flow


class Item(models.Model):
    item_id = models.AutoField(primary_key=True, db_index=True)
    name = models.CharField(max_length=64)
    flow = models.ForeignKey(Flow, on_delete=models.CASCADE)
    slug = models.SlugField(editable=False, max_length=200, blank=True)

    def save(self, *args, **kwargs):
        slug = "%s.%s" % (slugify(self.flow.title), slugify(self.name))
        self.slug = slug
        for element in self.element_set.all():
            element.save()
        super(Item, self).save(*args, **kwargs)

    def to_dict(self):
        return {
            "id": self.item_id,
            "name": self.name,
            "flowId": self.flow.flow_id,
            "slug": self.slug
        }

    def update(self, data: dict) -> None:
        self.name = data["name"]
        self.element_set.all().delete()

        element_index = 0
        for element in sorted(data["elements"], key=itemgetter("description")):
            element.pop('id', None)
            element.pop('itemId', None)
            element["item"] = self
            element["index"] = element_index
            new_element = Element(**element)
            element_index += 1
            new_element.save()

        self.save()

    def get_context(self) -> dict:
        context = self.to_dict()
        context["elements"] = []
        for element in self.element_set.all():
            context_element = element.to_dict()
            context["elements"].append(context_element)
        return context

    def __str__(self):
        return self.slug
