<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BackButtonComponent v-if="allowsUndo" :input-id="'return'"></BackButtonComponent>
      <HeaderImageComponent v-if="image" v-bind="image"></HeaderImageComponent>
      <BaseTitleComponent v-if="title" v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent v-if="description" v-bind="description"></BaseDescriptionComponent>
      <PopupComponent v-if="popup" v-bind="popup"></PopupComponent>
      <BaseDescriptionComponent v-if="additionalDescription" v-bind="additionalDescription"></BaseDescriptionComponent>
    </div>
    <div id="interface-actions" class="content">
      <ActionGroupComponent v-if="actions" :actions="actions"></ActionGroupComponent>
    </div>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import BackButtonComponent from "@/components/BackButtonComponent";
import BaseDescriptionComponent from "armory-sdk/src/components/BaseDescriptionComponent";
import ActionGroupComponent from "armory-sdk/src/components/ActionGroupComponent";
import HeaderImageComponent from "@/components/HeaderImageComponent";
import PopupComponent from "@/components/PopupComponent";

export default {
  name: "OfferInterface",
  props: {
    title: Object,
    image: Object,
    description: Object,
    actions: Array,
    allowsUndo: Boolean,
    popup: Object,
    additionalDescription: Object,
  },
  components: {
    PopupComponent,
    ActionGroupComponent,
    BaseDescriptionComponent,
    HeaderImageComponent,
    BaseTitleComponent,
    BackButtonComponent,
  },
};
</script>

<style scoped></style>
