<template>
  <div id="centered-interface" class="interface">
    <BaseImageComponent v-if="image" v-bind="image"></BaseImageComponent>
    <BaseTitleComponent v-if="text" v-bind="text"></BaseTitleComponent>
  </div>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import BaseImageComponent from "armory-sdk/src/components/BaseImageComponent";

export default {
  name: "CenteredContentInterface",
  components: {
    BaseTitleComponent,
    BaseImageComponent,
  },
  props: {
    image: Object,
    text: Object,
  },
};
</script>

<style scoped>
#centered-interface.interface {
  justify-content: center;
  text-align: center;
}
</style>
