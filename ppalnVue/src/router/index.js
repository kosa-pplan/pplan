import Vue from 'vue';
import VueRouter from "vue-router";
import route from './route';
import pageNotFoundCompo from "@/components/Util/PageNotFoundCompo.vue";

Vue.use(VueRouter);
const base_router = [{path:'*', component: pageNotFoundCompo}];

export default new VueRouter({
    //mode의 기본 값은 hash이고 abstract, history가 있음
    mode: 'history',
    routes: [...route, ...base_router],
});