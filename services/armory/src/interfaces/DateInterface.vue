<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BaseTitleComponent class="armando-title" text="Armando"></BaseTitleComponent>
      <BaseTitleComponent text="Odaberi datum koji ti je najzgodniji."></BaseTitleComponent>
    </div>
        <ActionGroupComponent
            v-if="dates"
            :actions="dates"
        ></ActionGroupComponent>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import ActionGroupComponent from "armory-sdk/src/components/ActionGroupComponent";

export default {
  name: "DateInterface",
  props: {
    title: Object,
    dates: Array,
  },
  components: {
    ActionGroupComponent,
    BaseTitleComponent,
  },
};
</script>

<style scoped>
.armando-title {
    font-size: 2em;
}
</style>
