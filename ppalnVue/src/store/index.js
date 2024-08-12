// Vue와 Vuex를 가져옵니다.
import Vue from "vue";
import Vuex from "vuex";
import { getToken, decodeJWT } from '@/utils/auth'; // JWT 관련 유틸리티 함수 추가

Vue.use(Vuex);

// 이 모듈에는 상태(state), 뮤테이션(mutations), 액션(actions), 게터(getters)가 포함되어 있습니다.


const store = new Vuex.Store({
    state: {
        items: [],
        nextIdx: 1,
        selectedColor : '', //ㅊㄱ
        selectedCategory : '맛집', //ㅊㄱ
        token: getToken(), // 초기 상태에서 JWT 토큰을 로컬 스토리지에서 가져옴
        userEmail: null, // JWT 토큰에서 추출한 사용자 이메일을 저장할 변수
      },
      mutations: {
        ADD_ITEM(state, { name,address='',business="" ,category=""}) {
          state.items.push({
            name: name,
            address: address,
            business: business,
            category:category,
            idx: state.nextIdx++
          });
        },
        DELETE_ITEM(state, id) {
          state.items = state.items.filter(item => item.idx !== id);
        },
        UPDATE_ITEMS(state, newItems) {
          state.items = newItems;
        },
        setColor(state, color) {
          state.selectedColor = color; // ㅊㄱ
        },
        setCategory(state, category) {
          state.selectedCategory = category; // ㅊㄱ
        },
        setToken(state, token) {
          state.token = token;
          state.userEmail = token ? decodeJWT(token).sub : null; // JWT 토큰에서 이메일을 추출하여 상태에 저장
        },
        clearAuth(state) {
            state.token = null;
            state.userEmail = null;
        }
      },
      actions: {
        addItem({ commit }, item) {
            commit('ADD_ITEM', item);
          },
        deleteItem({ commit }, id) {
          commit('DELETE_ITEM', id);
        },
        updateItems({ commit }, newItems) {
          commit('UPDATE_ITEMS', newItems);
        },
        updateColor({ commit }, color) {
          commit('setColor', color); // ㅊㄱ
        },
        updateCategory({ commit }, category) {
          commit('setCategory', category); // ㅊㄱ
        },
        initializeAuth({ commit }) {
          const token = getToken();
          commit('setToken', token); // JWT 토큰을 초기화하고 상태에 설정
        },
        logout({ commit }) {
            commit('clearAuth'); // 인증 정보를 초기화 (로그아웃)
            localStorage.removeItem('token'); // 로컬 스토리지에서 토큰 제거
        }
      },
      getters: {
        items: state => state.items,
        filteredItems: state => {
          return state.items.filter(item => item.category === state.selectedCategory);
        },
        selectedColor: state => state.selectedColor,
        selectedCategory: state => state.selectedCategory, //ㅊㄱ
        isAuthenticated: state => !!state.token, // 사용자가 인증되었는지 여부를 반환
        getUserEmail: state => state.userEmail, // JWT 토큰에서 추출한 사용자 이메일을 반환
      }
});

// 스토어의 초기 상태를 콘솔에 출력하여 스토어가 올바르게 생성되었는지 확인합니다.
// 여기서는 'countxs' 모듈의 초기 상태를 출력합니다.
console.log('index.js', store.state.items);

export default store;