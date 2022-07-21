<template>
  <div class="editor options-editor">
    <div class="editor-header">
      <div class="editor-label" :style="{ backgroundColor: color }">Options Editor</div>
    </div>
    <div class="editor-content">
      <div class="options-items">
        <div class="options-item" v-for="(item, index) in internalContent" :key="index">
          <div class="field first">
            <label class="label">Text</label>
            <div class="control">
              <textarea
                class="input"
                type="text"
                placeholder="Enter a text shown to user"
                v-model="item.text"
              />
            </div>
          </div>
          <div class="field first" v-show="developerMode">
            <label class="label">Internal Value</label>
            <div class="control">
              <input
                class="input"
                type="text"
                placeholder="Enter a value for developers"
                v-model="item.id"
              />
            </div>
          </div>
          <div class="field field-button">
            <button class="button is-danger" @click.prevent="removeItem(index)">Remove</button>
          </div>
          <p
            v-if="!internalContent || !internalContent.length"
            class="notice"
          >Please add options by pressing on the button below.</p>
        </div>
      </div>
      <button @click="add" class="button is-small is-dark add-item-button">
        <img src="../../../assets/plus.svg" alt="">
        Add new option
        </button>
    </div>
  </div>
</template>

<script>
import TypeToColorMap from "@/constants/typeToColorMap";

import { mapState, mapActions } from "vuex";

const tryToParseOptions = str => {
  try {
    const options = JSON.parse(str);
    if (!Array.isArray(options)) return [];
    // Todo add maybe more checks
    return options;
  } catch (e) {
    return [];
  }
};

export default {
  props: {
    element: {
      required: true
    }
  },
  data() {
    return {
      internalContent: tryToParseOptions(this.element.content)
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
      this.internalContent = tryToParseOptions(val);
    }
  },
  computed: {
    ...mapState(["elements", "developerMode"]),
    value() {
      return this.elements.find(element => element.index === this.element.index).content;
    },
    color() {
      return TypeToColorMap["OPTIONS"];
    }
  },
  methods: {
    ...mapActions(["updateElement"]),
    add() {
      this.internalContent.push({ text: "", id: "" });
    },
    removeItem(index) {
      this.internalContent.splice(index, 1);
    }
  }
};
</script>

<style scoped lang="scss">
.options-items {
  margin-bottom: 0.5rem;
}
.options-item {
  display: flex;
  align-items: center;
  .field {
    flex: 1;
  }
  .field.first {
    margin-right: 1rem;
  }
  .field-button {
    margin-top: 19px;
    flex: 0;
  }
  .select,
  .select select {
    width: 100%;
  }
}
.input {
  resize: vertical;
}
.add-item-button img {
  width: 15px;
  display: inline-block;
  margin-right: 0.4rem;
}
</style>