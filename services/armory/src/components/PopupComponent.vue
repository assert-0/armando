<template>
  <section class="content">
      <div :class="classes()">
        <div class="modal-background"></div>
        <div class="modal-content">
          <div class="box">
            <BaseTitleComponent name="popup-title" v-if="title" v-bind="title"></BaseTitleComponent>
            <BaseDescriptionComponent name="popup-description" v-if="description" v-bind="description"></BaseDescriptionComponent>
          </div>
        </div>
        <button class="modal-close is-large" @click="toggleModal" aria-label="close"></button>
      </div>
    <a @click="toggleModal">{{ triggerText }}</a>
  </section>
</template>

<script>
import BaseTitleComponent from "armory-sdk/src/components/BaseTitleComponent";
import BaseDescriptionComponent from "armory-sdk/src/components/BaseDescriptionComponent";

export default {
  name: "PopupComponent",
  components: {
    BaseTitleComponent,
    BaseDescriptionComponent,
  },
  props: {
    title: Object,
    description: Object,
    triggerText: String,
  },
  data() {
    return {
      modal: false,
    };
  },
  methods: {
    toggleModal() {
      this.modal = !this.modal;
      console.log(this.modal);
    },
    classes() {
      return this.modal ? "modal is-active" : "modal";
    },
  },
};
</script>

<style scoped>
.modal-close {
  border-radius: 100% !important;
}
.modal-content {
  width: 90%;
  max-width: 480px;
}

.box {
  height: 100%;
}
</style>
