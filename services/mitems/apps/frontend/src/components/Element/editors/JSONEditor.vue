<template>
  <div class="editor json-editor">
    <div class="editor-header">
      <div class="editor-label" :style="{ backgroundColor: color }">JSON Editor</div>
    </div>
    <div class="editor-content">
      <v-jsoneditor v-model="internalContent" :options="options" height="400px" />
    </div>
    <!-- <textarea class="textarea" placeholder="Enter the content" :value="value" @input="change"></textarea> -->
  </div>
</template>

<script>
import TypeToColorMap from "@/constants/typeToColorMap";
import VJsoneditor from "v-jsoneditor";

import { mapState, mapActions } from "vuex";

const tryToParseJSON = str => {
  try {
    return JSON.parse(str);
  } catch (e) {
    return {};
  }
};

export default {
  props: {
    element: {
      required: true
    }
  },
  components: { VJsoneditor },
  data() {
    return {
      internalContent: tryToParseJSON(this.element.content),
      options: {
        mode: "code",
        mainMenuBar: false
      }
    };
  },
  watch: {
    internalContent: {
      deep: true,
      handler(val) {
        this.updateElement({
          index: this.element.index,
          value: JSON.stringify(val)
        });
      }
    },
    value(val) {
      this.internalContent = tryToParseJSON(val);
    }
  },
  computed: {
    ...mapState(["elements"]),
    value() {
      return this.elements.find(element => element.index === this.element.index).content;
    },
    color() {
      return TypeToColorMap["JSON"];
    }
  },
  methods: {
    ...mapActions(["updateElement"])
  }
};
</script>

<style lang="scss">
@import "@/styles/variables";
.jsoneditor {
  border-color: $primary;
}
</style>