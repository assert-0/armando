<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BaseTitleComponent class="armando-title" text="Armando"></BaseTitleComponent>
      <BaseTitleComponent text="Hvala na odgovoru, očekujte poziv od Sergia!"></BaseTitleComponent>
    </div>
        <ActionGroupComponent
            v-if="submit"
            :actions="submit"
        ></ActionGroupComponent>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import ActionGroupComponent from "armory-sdk/src/components/ActionGroupComponent";
import BaseSpacerComponent from "armory-sdk/src/components/BaseSpacerComponent";

export default {
  name: "RateInterface",
  props: {
    title: Object,
    submit: Array,
  },
  components: {
    ActionGroupComponent,
    BaseTitleComponent,
    BaseSpacerComponent,
  },
};
</script>

<style scoped>
.armando-title {
    font-size: 2em;
}
</style>
