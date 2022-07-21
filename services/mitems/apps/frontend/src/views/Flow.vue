<template>
  <div class="flow">
    <section-title v-if="flow">
      <template slot="title">
        <div class="breadcrumbs">
          <router-link to="/">
            <img src="../assets/home.svg" class="home-icon" />
          </router-link>
          <ChevronRight />
          <h1 class="title is-3 is-marginless mr-2">{{ flow.title }}</h1>
          <router-link class="button is-dark" :to="`/flows/${flow.id}/edit`">Edit</router-link>
        </div>
      </template>
      <template slot="actions">
        <router-link :to="`/flows/${flow.id}/items/add`" class="button is-primary">Add new item</router-link>
      </template>
    </section-title>

    <div class="items" v-if="flow && flow.items">
      <item
        v-for="item in flow.items"
        :key="item.id"
        :flow="flow"
        :item="item"
        :elements="item.elements"
      ></item>
    </div>
  </div>
</template>

<script>
import SectionTitle from "@/components/SectionTitle.vue";
import Item from "@/components/Item.vue";
import api from "@/api";
import ChevronRight from "@/components/ChevronRight.vue";

import { mapActions } from "vuex";

export default {
  name: "Flows",
  components: {
    SectionTitle,
    Item,
    ChevronRight
  },
  data() {
    return {
      flow: null,
      elementsMap: null
    };
  },
  methods: {
    ...mapActions(["startLoading", "stopLoading"])
  },
  async mounted() {
    this.startLoading();
    const id = this.$route.params.id;
    try {
      const data = await api.flows.get(id);
      this.flow = data.flow;
    } catch (e) {
      this.$toast.error(e.message);
    }
    this.stopLoading();
  }
};
</script>
<style lang="scss" scoped>
.flows-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  column-gap: 1rem;
  row-gap: 1rem;
}
</style>
