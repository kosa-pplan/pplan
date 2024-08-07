import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index'
import { VueEditor } from 'vue2-editor';
import store from './store'

Vue.component('VueEditor', VueEditor);

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  store,
  router,
}).$mount('#app')
