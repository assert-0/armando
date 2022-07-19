<template>
  <div>
    <div id="image-wrapper" :class="this.wrapperClasses()">
      <img :class="this.classes()" :src="src" rel="preload"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "HeaderImageComponent",
  props: {
    src: String,
    hasMargin: Boolean,
  },
  methods: {
    classes: () => {
      return "position-top";
    },
    wrapperClasses() {
      if (this.hasMargin) return "component content has-margin";
      return "component content";
    },
  },
};
</script>

<style scoped>
#image-wrapper {
  text-align: center;
}
@media (max-width: 767px) {
  #image-wrapper {
    margin: -40px -32px 27px -42px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .has-margin {
    margin-top: -16px !important;
  }
}
</style>
