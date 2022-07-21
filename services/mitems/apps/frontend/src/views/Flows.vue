<template>
  <div class="flows">
    <section-title>
      <template slot="title">
        <h1 class="title is-2">Flows</h1>
      </template>
      <template slot="actions">
        <router-link to="/flows/add" class="button is-primary">Add new flow</router-link>
      </template>
    </section-title>

    <div class="flows-grid">
      <flow v-for="flow in flows" :key="flow.id" :flow="flow"></flow>
    </div>
  </div>
</template>

<script>
import SectionTitle from "@/components/SectionTitle.vue";
import Flow from "@/components/Flow.vue";
import api from "@/api";

import { mapActions } from "vuex";

export default {
  name: "Flows",
  components: {
    SectionTitle,
    Flow
  },
  data() {
    return {
      flows: []
    };
  },
  methods: {
    ...mapActions(['startLoading', 'stopLoading'])
  },
  async mounted() {
    this.startLoading();
    try {
      this.flows = await api.flows.all();
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
