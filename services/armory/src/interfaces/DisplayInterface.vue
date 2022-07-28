<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BaseTitleComponent class="armando-title" text="Armando"></BaseTitleComponent>
      <hr class="break">
      <BaseSpacerComponent height="54px"></BaseSpacerComponent>
      <BaseImageComponent v-if="image" v-bind="image"></BaseImageComponent>
      <BaseTitleComponent v-if="title" v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent
        v-if="description"
        v-bind="description"
      ></BaseDescriptionComponent>
      <BaseImageComponent  v-if="dataSrc" v-bind="dataSrc" :src=dataSrc ></BaseImageComponent>
    </div>
    <ActionGroupComponent
      v-if="activities"
            :actions="activities"
        ></ActionGroupComponent>
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
    activities: Array,
    dataSrc: String,
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

<style scoped>
.armando-title {
    font-size: 1.7em;
}
.estates {
  display: flex;
  overflow-y: hidden;
}
.break {
    border: 0.5px solid #c3c3d0;
    height: 0.5px;
}
</style>
