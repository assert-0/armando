<template>
  <div class="flows">
    <section-title>
      <template slot="title">
        <h1 class="title is-2">Delete "{{ name }}"?</h1>
      </template>
      <template slot="actions">
        <router-link :to="redirectLink" class="button is-secondary mr-1">Cancel</router-link>
        <a
          href="#"
          class="button is-danger"
          @click.prevent="remove"
          :class="{'is-loading': deleting}"
        >Delete</a>
      </template>
    </section-title>
  </div>
</template>

<script>
import SectionTitle from "@/components/SectionTitle.vue";
import api from "@/api";

import { mapActions } from "vuex";

export default {
  name: "Flows",
  components: {
    SectionTitle
  },
  data() {
    return {
      object: null,
      deleting: false
    };
  },
  computed: {
    name() {
      if (!this.object) {
        return "";
      }
      if (this.what === "item") {
        return this.object.name;
      }
      return this.object.title;
    },
    redirectLink() {
      if (!this.object) {
        return "/";
      }
      if (this.what === "item") {
        return `/flows/${this.object.flowId}`;
      } else {
        return "/";
      }
    }
  },
  methods: {
    async remove() {
      this.deleting = true;
      if (this.what === "item") {
        await api.items.delete(this.id);
      } else {
        await api.flows.delete(this.id);
      }
      this.deleting = false;
      this.$router.push(this.redirectLink);
    },
    ...mapActions(["startLoading", "stopLoading"])
  },
  async mounted() {
    this.startLoading();
    const what = this.$route.params.what;
    this.what = what;
    const id = this.$route.params.id;
    this.id = id;
    try {
      if (this.what === "item") {
        const data = await api.items.get(this.id);
        this.object = data.item;
      } else if (this.what === "flow") {
        const data = await api.flows.get(this.id);
        this.object = data.flow;
      } else {
        throw new Error("Unknown object type");
      }
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
