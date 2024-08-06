import Vue from 'vue';
import VueRouter from "vue-router";

Vue.use(VueRouter);
export default new VueRouter({
    //mode의 기본 값은 hash이고 abstract, history가 있음
    mode : 'history',
});