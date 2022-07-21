<template>
  <div class="item" sticky-container>
    <section-title v-if="flow && item" v-sticky>
      <template slot="title">
        <div class="breadcrumbs">
          <router-link to="/">
            <img src="../assets/home.svg" class="home-icon" />
          </router-link>
          <ChevronRight />
          <router-link
            :to="`/flows/${this.flowId}`"
            class="title is-3 is-marginless"
          >{{ flow.title }}</router-link>
          <ChevronRight />
          <div class="title is-3 is-marginless" style="font-weight: 400">{{ item.name }}</div>
        </div>
      </template>
      <template slot="actions">
        <router-link :to="`/flows/${this.flowId}`" class="button is-secondary mr-1">Cancel</router-link>
        <a
          href="#"
          class="button is-primary"
          @click.prevent="submit"
          :class="{'is-loading': saving}"
        >Save changes</a>
      </template>
    </section-title>

    <div class="row" v-if="item">
      <div class="col-6">
        <panel class="item">
          <div class="field">
            <label class="label">Name</label>
            <div class="control">
              <input
                class="input"
                type="text"
                placeholder="Enter the name of the item"
                :disabled="developerMode === false"
                v-model="item.name"
              />
            </div>
          </div>
          <div class="field" v-if="developerMode">
            <label class="label">Code snippet</label>
            <div class="control">
              <pre>Mitems.set_context("{{ item.slug }}")</pre>
            </div>
          </div>
        </panel>
      </div>
    </div>

    <div class="elements" v-if="item && elements">
      <h3 class="title is-4">Elements</h3>
      <v-element
        v-for="element in elements"
        :key="element.id"
        :element="element"
        @remove="askAndRemoveElement(element.index)"
      ></v-element>
    </div>

    <div
      class="empty-state"
      v-if="!elements || !elements.length"
    >Press the button below to add new element, for example text or options.</div>

    <div class="add-element">
      <button @click="newElement" class="button is-dark add-element-button">
        <img src="../assets/plus.svg" alt />
        Add new element
      </button>
    </div>
  </div>
</template>

<script>
import SectionTitle from "@/components/SectionTitle.vue";
import Element from "@/components/Element/Element.vue";
import Panel from "@/components/Panel.vue";
import ChevronRight from "@/components/ChevronRight.vue";
import ElementTypes from "@/constants/elementTypes";
import getElementIndex from "@/utils/getElementIndex";

import api from "@/api";
import { mapActions, mapState } from "vuex";
export default {
  name: "items",
  components: {
    SectionTitle,
    VElement: Element,
    Panel,
    ChevronRight
  },
  data() {
    return {
      id: null,
      flow: null,
      item: null,
      saving: false
    };
  },
  computed: {
    ...mapState(["elements", "developerMode"])
  },
  methods: {
    ...mapActions([
      "startLoading",
      "stopLoading",
      "setElements",
      "addElement",
      "removeElement"
    ]),
    async submit() {
      // TODO: validation
      if (!this.item.name) {
        return;
      }
      this.saving = true;
      if (this.id) {
        await api.items.update(this.id, {
          ...this.item,
          elements: this.elements
        });
      } else {
        await api.items.create(this.flowId, {
          ...this.item,
          elements: this.elements
        });
      }
      this.saving = false;
      this.$router.push(`/flows/${this.flowId}`);
    },
    newElement() {
      this.addElement({
        description: "",
        type: ElementTypes[0].value,
        content: ""
      });
    },
    askAndRemoveElement(index) {
      if (!confirm("Please confirm deleting of the element.")) return;
      this.removeElement(index);
    }
  },
  async mounted() {
    this.startLoading();
    // Getting flow
    const flowId = this.$route.params.flowId;
    this.flowId = flowId;
    const flowData = await api.flows.get(flowId);
    this.flow = flowData.flow;

    // Getting item if editing
    const id = this.$route.params.id;
    this.id = id;
    if (this.id) {
      try {
        const data = await api.items.get(id);
        this.setElements(
          data.item.elements.map(element => {
            element.index = getElementIndex();
            return element;
          })
        );
        this.item = data.item;
      } catch (e) {
        this.$toast.error(e.message);
      }
    } else {
      this.item = {
        name: "",
        elements: []
      };
    }
    this.stopLoading();
  },
  destroyed() {
    this.setElements([]);
  }
};
</script>
<style lang="scss" scoped>
.item {
  margin-bottom: 1rem;
}
.elements > div {
  margin-bottom: 1rem;
}
.elements h3 {
  margin: 1.5rem 0 0.75rem;
}
.add-element {
  text-align: center;
  padding: 1rem;
}
.add-element-button img {
  width: 20px;
  display: inline-block;
  margin-right: 0.5rem;
}
</style>