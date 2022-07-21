<template>
  <div class="flow-form">
    <section-title>
      <template slot="title">
        <h1 class="title is-2">{{ title }}</h1>
      </template>
      <template slot="actions">
        <router-link :to="cancelLink" class="button is-secondary mr-1">Cancel</router-link>
        <a
          href="#"
          class="button is-primary"
          @click.prevent="submit"
          :class="{'is-loading': saving}"
        >Save changes</a>
      </template>
    </section-title>
    <div class="row">
      <div class="col-6">
        <panel>
          <div class="field">
            <label class="label">Title</label>
            <div class="control">
              <input
                class="input"
                type="text"
                :disabled="developerMode === false"
                placeholder="Enter the title of the flow"
                v-model="flow.title"
              />
            </div>
          </div>
          <div class="field">
            <label class="label">Description</label>
            <div class="control">
              <textarea
                class="textarea"
                placeholder="Enter something that describes the flow"
                v-model="flow.description"
              ></textarea>
            </div>
          </div>
        </panel>
      </div>
    </div>
  </div>
</template>

<script>
import SectionTitle from "@/components/SectionTitle.vue";
import Panel from "@/components/Panel.vue";
import api from "@/api";
import { mapState, mapActions } from "vuex";

export default {
  components: {
    SectionTitle,
    Panel
  },
  data() {
    return {
      id: null,
      flow: {},
      saving: false
    };
  },
  computed: {
    ...mapState(["flows", "developerMode"]),
    value() {
      return this.flows.find(flow => flow.index === this.flow.index).content;
    },
    title() {
      return this.id
        ? this.flow
          ? this.flow.title
          : "Editing a flow"
        : "Adding a flow";
    },
    cancelLink() {
      return this.id ? (this.flow ? `/flows/${this.flow.id}` : "/") : "/";
    }
  },
  methods: {
    async submit() {
      // TODO: validation
      if (!this.flow.title) {
        return;
      }
      this.saving = true;
      if (this.id) {
        await api.flows.update(this.id, this.flow);
      } else {
        await api.flows.create(this.flow);
      }
      this.saving = false;
      if (this.id) {
        this.$router.push(`/flows/${this.id}`);
      } else {
        this.$router.push("/");
      }
    },
    ...mapActions(["startLoading", "stopLoading"])
  },
  async mounted() {
    this.startLoading();
    const id = this.$route.params.id;
    this.id = id;
    if (this.id) {
      try {
        const data = await api.flows.get(id);
        this.flow = data.flow;
      } catch (e) {
        this.$toast.error(e.message);
      }
    } else {
      this.flow = {
        title: "",
        description: ""
      };
    }
    this.stopLoading();
  }
};
</script>

<style scoped lang="scss">
</style>