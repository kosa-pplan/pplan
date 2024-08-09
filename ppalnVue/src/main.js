import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index'
import {VueEditor} from 'vue2-editor';
import store from './store'
import axios from "axios";
import 'vue2-dropzone/dist/vue2Dropzone.min.css';
import { VueDropzone } from 'vue2-dropzone';


Vue.component('VueEditor', VueEditor);
Vue.component('VueDropzone', VueDropzone);
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$serverUrl = '//localhost:8080';

new Vue({
    render: h => h(App),
    store,
    router,
}).$mount('#app')