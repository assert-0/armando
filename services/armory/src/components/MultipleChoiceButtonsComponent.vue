<template>
  <div id="multiple-choice">
    <div id="options" v-for="option in options" :key="option.id">
      <div><input :class="classes(option.id)" type="button" :id="option.id" :value="option.text" @click="toggle"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MultipleChoiceButtonsComponent",
  props: {
    inputId: String,
    options: Array,
  },
  mounted: function () {
    this.$store.commit("setInputValue", {
      inputId: this.inputId,
      value: {
        selectedInputs: [],
      },
    });
  },
  computed: {
    inputValue() {
      if (this.$store.state.inputs[this.inputId])
        return this.$store.state.inputs[this.inputId].selectedInputs;
      return [];
    },
  },
  methods: {
    classes(optionId) {
      return this.inputValue.includes(optionId) ? "selected button" : "button";
    },
    toggle(event) {
      let currentSelected = this.inputValue;
      console.log("Event: " + event + " " + currentSelected);
      let index = currentSelected.indexOf(event.target.id);
      if (index !== -1) currentSelected.splice(index, 1);
      else currentSelected.push(event.target.id);
      this.$store.commit("setInputValue", {
        inputId: this.inputId,
        value: {
          selectedInputs: currentSelected,
        },
      });
    }
  },
}
</script>

<style scoped>

#options {
  display: flex;
  flex-direction: column;
  margin: 1%;
}
#multiple-choice{
  horiz-align: center;
}
.selected{
  color: green !important;
  text-blink: true;
}
</style>