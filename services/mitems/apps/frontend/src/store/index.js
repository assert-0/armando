import Vue from "vue";
import Vuex from "vuex";
import getElementIndex from '@/utils/getElementIndex';

Vue.use(Vuex);

const loadDeveloperMode = () => {
  const str = localStorage.getItem("developer-mode");
  try {
    return str ? JSON.stringify(str) : false;
  } catch (e) {
    return false;
  }
}

const saveDeveloperMode = (value) => {
  localStorage.setItem("developer-mode", value);
}

export default new Vuex.Store({
  state: {
    loading: false,
    elements: [],
    developerMode: loadDeveloperMode()
  },
  mutations: {
    SET_LOADING(state, value) {
      state.loading = value;
    },
    SET_ELEMENTS(state, elements) {
      state.elements = elements;
    },
    UPDATE_ELEMENT_AT_INDEX(state, { index, value }) {
      const element = state.elements.find(element => element.index === index);
      element.content = value;
    },
    ADD_ELEMENT(state, element) {
      element.index = getElementIndex();
      state.elements.push(element)
    },
    REMOVE_ELEMENT(state, index) {
      state.elements = state.elements.filter(element => element.index !== index);
    },
    SET_DEVELOPER_MODE(state, value) {
      state.developerMode = value;
      saveDeveloperMode(value);
    }
  },
  actions: {
    startLoading({ commit }) {
      commit("SET_LOADING", true);
    },
    stopLoading({ commit }) {
      commit("SET_LOADING", false);
    },
    setElements({ commit }, elements) {
      commit("SET_ELEMENTS", elements);
    },
    addElement({ commit }, element) {
      commit('ADD_ELEMENT', element);
    },
    updateElement({ commit }, { index, value }) {
      commit("UPDATE_ELEMENT_AT_INDEX", { index, value });
    },
    removeElement({ commit }, index) {
      commit('REMOVE_ELEMENT', index);
    },
    setDeveloperMode({ commit }, value) {
      commit('SET_DEVELOPER_MODE', value);
    }
  },
  modules: {}
});
