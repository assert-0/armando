<template>
  <section>
<!--    <div v-if="type === 'video'" class="video-container">-->
<!--      <vue-player v: :autoplay="true" :src="src"/>-->
<!--    </div>-->
    <div v-if="type === 'image'" class="image-container">
      <ShadowImageComponent :src="src" alt="Invalid image format" />
    </div>
  </section>
</template>

<script>
// import vuePlayer from "@algoz098/vue-player";

export default {
  name: "VideoComponent",
  components: {
    // vuePlayer,
  },
  props: {
    type: String,
    src: String

  }
}
</script>

<style>

</style>