<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <StreakComponent v-bind="streak"></StreakComponent>
      <BaseTitleComponent v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent v-bind="description"></BaseDescriptionComponent>
    </div>
    <div id="interface-actions" class="content">
      <PrimarySubmitButtonComponent v-bind="submitButton"></PrimarySubmitButtonComponent>
    </div>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import BaseDescriptionComponent from "armory-sdk/src/components/BaseDescriptionComponent";
import PrimarySubmitButtonComponent from "armory-sdk/src/components/PrimarySubmitButtonComponent";
import StreakComponent from "../components/StreakComponent";

export default {
  name: "StreakInterface",
  props: {
    title: Object,
    image: Object,
    streak: Object,
    description: Object,
    submitButton: Object,
  },
  components: {
    PrimarySubmitButtonComponent,
    BaseDescriptionComponent,
    StreakComponent,
    BaseTitleComponent,
  },
};
</script>

<style scoped>
.content{
  text-align: center;
}
</style>




