<template>
  <div class="card">
    <div class="content" v-if="$slots.content">
      <slot name="content" />
    </div>
    <div class="actions" v-if="$slots.actions">
      <slot name="actions" />
    </div>
    <slot />
  </div>
</template>

<style scoped lang="scss">
@import "@/styles/variables";
.card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: $size-4;
  background: #ffffff;
  box-shadow: $shadow-basic;
  border-radius: 10px;
}

.card .content {
  flex: 1;
  margin-right: $size-3;
}
</style>