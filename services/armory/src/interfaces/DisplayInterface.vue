<template>
  <div class="interface">
    <div id="interface-content" class="content">
      <BaseTitleComponent class="armando-title" text="Armando"></BaseTitleComponent>
      <BaseSpacerComponent height="54px"></BaseSpacerComponent>
      <BaseImageComponent v-if="image" v-bind="image"></BaseImageComponent>
      <BaseTitleComponent v-if="title" v-bind="title"></BaseTitleComponent>
      <BaseDescriptionComponent
        v-if="description"
        v-bind="description"
      ></BaseDescriptionComponent>
    </div>
    <div>        
      <BaseTitleComponent v-if="spTitle" v-bind="spTitle"></BaseTitleComponent>
      <div id="same-price-estates" class="content estates">
            <div class="estate">
                <BaseImageComponent v-if="spImage1" v-bind="spImage1"></BaseImageComponent>
                <ActionGroupComponent
                    v-if="spActions1"
                    :actions="spActions1"
                ></ActionGroupComponent>
            </div>
            <div class="estate">
                <BaseImageComponent v-if="spImage2" v-bind="spImage2"></BaseImageComponent>
                <ActionGroupComponent
                    v-if="spActions2"
                    :actions="spActions2"
                ></ActionGroupComponent>
            </div>
            <div class="estate">
                <BaseImageComponent v-if="spImage3" v-bind="spImage3"></BaseImageComponent>
                <ActionGroupComponent
                    v-if="spActions3"
                    :actions="spActions3"
                ></ActionGroupComponent>
            </div>
        </div>
    </div>
    <BaseSpacerComponent height="20px"></BaseSpacerComponent>
    <div>
        <BaseTitleComponent v-if="slTitle" v-bind="slTitle"></BaseTitleComponent>
        <div id="same-location-estates" class="content estates">
            <div class="estate">
                <BaseImageComponent v-if="slImage1" v-bind="slImage1"></BaseImageComponent>
                <ActionGroupComponent
                    v-if="slActions1"
                    :actions="slActions1"
                ></ActionGroupComponent>
            </div>
            <div class="estate">
                <BaseImageComponent v-if="slImage2" v-bind="slImage2"></BaseImageComponent>
                <ActionGroupComponent
                    v-if="slActions2"
                    :actions="slActions2"
                ></ActionGroupComponent>
            </div>
            <div class="estate">
                <BaseImageComponent v-if="slImage3" v-bind="slImage3"></BaseImageComponent>
                <ActionGroupComponent
                    v-if="slActions3"
                    :actions="slActions3"
                ></ActionGroupComponent>
            </div>
        </div>
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
    spTitle: Object,
    spImage1: Object,
    spActions1: Array,
    spImage2: Object,
    spActions2: Array,
    spImage3: Object,
    spActions3: Array,
    slTitle: Object,
    slImage1: Object,
    slActions1: Array,
    slImage2: Object,
    slActions2: Array,
    slImage3: Object,
    slActions3: Array,
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
