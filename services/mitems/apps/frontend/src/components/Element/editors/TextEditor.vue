<template>
  <div class="editor text-editor">
    <div class="editor-header">
      <div class="editor-label" :style="{ backgroundColor: color }">Text Editor</div>
    </div>
    <div class="editor-content">
      <textarea class="textarea" placeholder="Enter the content" :value="value" @input="change"></textarea>
    </div>
  </div>
</template>

<script>
import TypeToColorMap from "@/constants/typeToColorMap";

import { mapState, mapActions } from "vuex";

export default {
  props: {
    element: {
      required: true
    }
  },
  computed: {
    ...mapState(["elements"]),
    value() {
      return this.elements.find(element => element.index === this.element.index).content;
    },
    color() {
      return TypeToColorMap["TEXT"];
    }
  },
  methods: {
    ...mapActions(["updateElement"]),
    change(e) {
      this.updateElement({
        index: this.element.index,
        value: e.target.value
      });
    }
  }
};
</script>

<style scoped lang="scss">
</style>