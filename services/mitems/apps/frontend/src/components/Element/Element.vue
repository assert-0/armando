<template>
  <div class="element">
    <panel>
      <div v-if="developerMode === true" class="remove" @click="remove"
>
        <img src="../../assets/criss-cross.svg"/>
      </div>
      <div class="element-header">
        <div class="field first">
          <label class="label">Element name</label>
          <div class="control">
            <label>

              <input v-if="element.type === 'TEXT'"
                     class="input"
                     type="text"
                     :disabled="developerMode === false"
                     placeholder="Text"
                     v-model="element.description"
              />
              <input v-else-if="element.type === 'OPTIONS'"
                     class="input"
                     type="text"
                     :disabled="developerMode === false"
                     placeholder="Options"
                     v-model="element.description"
              />
              <input v-else
                     class="input"
                     type="text"
                     :disabled="developerMode === false"
                     placeholder="JSON"
                     v-model="element.description"
              />
            </label>
          </div>
        </div>
        <div class="field">
          <label class="label">Element type</label>
          <div class="control">
            <div class="select">
              <select v-model="element.type" :disabled="developerMode === false">
                <option v-for="type in types" :key="type.value" :value="type.value">{{ type.name }}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="field">
        <!-- <label class="label">Content</label> -->
        <div class="control">
          <component :is="getComponent(element.type)" :element="element"></component>
        </div>
      </div>
      <div class="field" v-if="element.id && developerMode">
        <label class="label">Code snippet</label>
        <div class="control">
          <pre v-if="element.type === 'TEXT'">{{ variableName }} = Mitems.get_text("{{ element.slug }}")</pre>
          <pre v-else-if="element.type === 'OPTIONS'">{{ variableName }} = Mitems.get_options("{{
              element.slug
            }}")</pre>
          <pre v-else>{{ variableName }} = Mitems.get_json("{{ element.slug }}")</pre>
        </div>
      </div>
    </panel>
  </div>
</template>

<script>
import Vue from "vue";
import Panel from "@/components/Panel.vue";
import ElementTypes from "@/constants/elementTypes";
import TypeToComponentMap from "@/constants/typeToComponentMap";
import slug from 'slug';

import {mapState} from "vuex";

Vue.component("text-editor", () => import("./editors/TextEditor.vue"));

Vue.component("json-editor", () => import("./editors/JSONEditor.vue"));

Vue.component("options-editor", () => import("./editors/OptionsEditor.vue"));

export default {
  props: {
    element: {
      required: true
    }
  },
  components: {
    Panel
  },
  data() {
    return {
      types: ElementTypes
    };
  },
  computed: {
    ...mapState(["developerMode"]),
    variableName() {
      return slug(this.element.description, "_").toLowerCase();
    }
  },
  methods: {
    getComponent(type) {
      return TypeToComponentMap[type];
    },
    remove() {
      this.$emit("remove");
    }
  }
};
</script>

<style scoped lang="scss">
@import "@/styles/variables";

.element {
  position: relative;
}

.element-header {
  display: flex;

  .field {
    flex: 1;
  }

  .field.first {
    margin-right: 1rem;
  }

  .select,
  .select select {
    width: 100%;
  }
}

.remove {
  position: absolute;
  top: -10px;
  right: -10px;

  img {
    width: 30px;
    cursor: pointer;
  }
}
</style>