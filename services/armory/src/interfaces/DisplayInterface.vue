<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BaseTitleComponent class="armando-title" text="Armando"></BaseTitleComponent>
      <BaseSpacerComponent height="54px"></BaseSpacerComponent>
      <BaseImageComponent v-if="image" v-bind="image"></BaseImageComponent>
      <BaseImageComponent  v-if="dataSrc" v-bind="dataSrc" :src=dataSrc ></BaseImageComponent>
      <BaseTitleComponent v-if="title" v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent
        v-if="description"
        v-bind="description"
      ></BaseDescriptionComponent>
    </div>
      <BaseTitleComponent v-if="rateTitle" v-bind="rateTitle"></BaseTitleComponent>
        <ActionGroupComponent
            v-if="rateBtn"
            :actions="rateBtn"
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
    rateTitle: Object,
    rateBtn: Array,
    dataSrc: String
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
    font-size: 2em;
}
.estates {
  display: flex;
  overflow-y: hidden;
}
.estate{
    min-width: 400px;
    max-height: 500px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin: 10px;
}
</style>
