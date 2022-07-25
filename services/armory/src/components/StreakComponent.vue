<template>
  <div class="component content">
    <div id="streak-value">
      <span>{{ value }}</span>

    </div>
  </div>
</template>

<script>
export default {
  name: "StreakComponent",
  props: {
    value: Number,
  },
};
</script>

<style scoped>
.component {
  text-align: center;
  display: flex;
  justify-content: center;
}
img {
  width: 50%;
}
#streak-value {
  height: clamp(62px, 37vw, 200px);
  width: clamp(50px, 30vw, 162px);
  background-image: url("../assets/img/streak-flame.png");
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
}
span {
  margin-top: 40%;
  font-size: clamp(50px, 13vw, 80px);
  font-weight: bold;
}
</style>