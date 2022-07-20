<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BaseSpacerComponent height="54px"></BaseSpacerComponent>
      <BaseImageComponent v-if="image" v-bind="image"></BaseImageComponent>
      <BaseTitleComponent v-if="title" v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent
        v-if="description"
        v-bind="description"
      ></BaseDescriptionComponent>
    </div>
    <div id="interface-actions" class="content">
      <ActionGroupComponent
        v-if="actions"
        :actions="actions"
      ></ActionGroupComponent>
    </div>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import BaseImageComponent from "armory-sdk/src/components/BaseImageComponent";
import BaseDescriptionComponent from "armory-sdk/src/components/BaseDescriptionComponent";
import ActionGroupComponent from "armory-sdk/src/components/ActionGroupComponent";
import BaseSpacerComponent from "armory-sdk/src/components/BaseSpacerComponent";

export default {
  name: "DisplayInterface",
  props: {
    title: Object,
    image: Object,
    description: Object,
    actions: Array,
  },
  components: {
    ActionGroupComponent,
    BaseDescriptionComponent,
    BaseImageComponent,
    BaseTitleComponent,
    BaseSpacerComponent,
  },
};
</script>

<style scoped></style>
