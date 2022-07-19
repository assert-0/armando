<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <HeaderImageComponent v-if="image" v-bind="image"></HeaderImageComponent>
      <SpacerComponent v-else></SpacerComponent>
      <BaseTitleComponent v-if="title" v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent v-if="description" v-bind="description"></BaseDescriptionComponent>
      <BaseTextInputComponent v-if="textInput" v-bind="textInput"></BaseTextInputComponent>
    </div>
    <div id="interface-actions" class="content">
      <ActionGroupComponent v-if="actions" :actions="actions"></ActionGroupComponent>
    </div>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import BaseDescriptionComponent from "armory-sdk/src/components/BaseDescriptionComponent";
import ActionGroupComponent from "armory-sdk/src/components/ActionGroupComponent";
import BaseTextInputComponent from "armory-sdk/src/components/BaseTextInputComponent";
import HeaderImageComponent from "@/components/HeaderImageComponent";
import SpacerComponent from "@/components/SpacerComponent";

export default {
  name: "DefaultInterface",
  props: {
    title: Object,
    image: Object,
    description: Object,
    textInput: Object,
    actions: Array,
    allowsUndo: Boolean,
  },
  components: {
    SpacerComponent,
    ActionGroupComponent,
    BaseDescriptionComponent,
    HeaderImageComponent,
    BaseTitleComponent,
    BaseTextInputComponent,
  },
};
</script>

<style scoped>
</style>
