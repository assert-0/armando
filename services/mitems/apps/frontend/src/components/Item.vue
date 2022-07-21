<template>
  <div class="item">
    <card>
      <router-link :to="`/flows/${flow.id}/items/${item.id}`" slot="content">
        <h3 class="item-title">{{ item.name }}</h3>
        <div class="item-description">
          <span
            class="editor-label"
            v-for="element in elements"
            :style="{ backgroundColor: getElementColor(element) }"
            :key="element.id"
          >{{ element.description }}</span>
        </div>
      </router-link>
      <template slot="actions">
        <div v-if="developerMode">
        <router-link
          :to="`/remove/item/${item.id}`"

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
import TypeToColorMap from "@/constants/typeToColorMap";

import {mapState} from "vuex";

export default {
  props: {
    flow: {
      required: true
    },
    item: {
      required: true
    },
    elements: {
      required: true
    }
  },
  computed: {
        ...mapState(["developerMode"]),
  },
  components: {
    Card
  },
  methods: {
    getElementColor(element) {
      return TypeToColorMap[element.type];
    }
  }
};
</script>

<style scoped lang="scss">
@import "@/styles/variables";
.item {
  margin-bottom: 1rem;
}
.item-title {
  font-size: 20px;
  line-height: 23px;
  color: #363636;
}
.item-description {
  display: flex;
  flex-wrap: wrap;
  row-gap: 0.5rem;
}
.card {
  background: rgb(255,255,255);
  background: linear-gradient(90deg, rgba(255,255,255,1) 0%, rgba(255,255,255,1) 49%, rgba(79,99,103,1) 250%);
}
.item-elements {
  color: $grey;
}
</style>
