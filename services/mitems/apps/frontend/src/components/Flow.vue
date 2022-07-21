<template>
  <div class="flow">
    <card>
      <router-link slot="content" :to="`/flows/${flow.id}`">
        <h3 class="flow-title">{{ flow.title }}</h3>
        <div class="flow-description">{{ flow.description }}</div>
      </router-link>
      <template slot="actions">
        <div v-if="developerMode">
        <router-link
          :to="`/remove/flow/${flow.id}`"
          class="button is-dark"
          @click.prevent="remove"

        >Delete</router-link>
          </div>
      </template>
    </card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import {mapState} from "vuex";

export default {
  props: {
    flow: {
      required: true
    }
  },
  computed: {
        ...mapState(["developerMode"]),
  },
  components: {
    Card
  }
};
</script>

<style scoped lang="scss">
@import "@/styles/variables";
.flow-title {
  font-size: 20px;
  line-height: 23px;
  color: #363636;
}
.card {
  background: rgb(255,255,255);
  background: linear-gradient(90deg, rgba(255,255,255,1) 0%, rgba(255,255,255,1) 49%, rgba(79,99,103,1) 250%);
}
.flow-description {
  color: $grey;
}
</style>