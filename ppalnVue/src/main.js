import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index'
import { VueEditor } from 'vue2-editor';

Vue.component('VueEditor', VueEditor);

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
