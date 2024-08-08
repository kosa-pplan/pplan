import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index'
import {VueEditor} from 'vue2-editor';
import store from './store'
import axios from "axios";

Vue.component('VueEditor', VueEditor);
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$serverUrl = '//localhost:8080';

new Vue({
  render: h => h(App),
  store,
  router,
}).$mount('#app')