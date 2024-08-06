// Vue와 Vuex를 가져옵니다.
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// 이 모듈에는 상태(state), 뮤테이션(mutations), 액션(actions), 게터(getters)가 포함되어 있습니다.
import CountStore from "@/store/vuex-store";

const store = new Vuex.Store({
    modules: {

        countxs: CountStore,
    },
});

// 스토어의 초기 상태를 콘솔에 출력하여 스토어가 올바르게 생성되었는지 확인합니다.
// 여기서는 'countxs' 모듈의 초기 상태를 출력합니다.
console.log('index.js', store.state.countxs);

export default store;