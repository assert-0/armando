<template>
  <div class="section-title">
    <div class="content">
      <slot name="title" />
    </div>
    <div class="actions">
      <slot name="actions" />
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "@/styles/variables";
.section-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: $size-3 0;
  background: $body-background-color;
  // margin: 0 -$size-3;
}

.section-title.top-sticky {
  width: 100% !important;
  left: 0 !important;
  right: 0 !important;
  padding: $size-3;
  border-bottom: 1px solid #ccc;
}

.section-title .content {
  flex: 1;
  margin: 0;
  margin-right: $size-3;
  display: flex;
  align-items: center;
}
</style>