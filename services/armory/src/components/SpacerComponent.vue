<template>
  <div class="component content" :style="{ [height]: height }"></div>
</template>

<script>
export default {
  name: "SpacerComponent",
  props: {
    height: String,
  },
};
</script>

<style scoped>
.component {
  height: 150px;
}
@media (min-width: 768px) {
  .component {
    display: none;
  }
}
</style>