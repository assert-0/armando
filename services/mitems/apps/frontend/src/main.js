import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import './mockApi'
import Sticky from 'vue-sticky-directive'


import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"
import axios from "axios"

axios.defaults.headers.common = {
  'X-CSRFToken': window.csrfToken
};

Vue.config.productionTip = false

Vue.use(Sticky)
Vue.use(Toast, {})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
