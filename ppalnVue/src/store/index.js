import Vue from "vue";
import Vuex from "vuex";
import { getToken, decodeJWT } from '@/utils/auth';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

// 새로운 플러그인을 정의합니다.
function resetStatePlugin(store) {
    store.subscribe((mutation) => {
        if (mutation.type === 'clearAuth') {
            localStorage.clear(); // 로컬 스토리지의 모든 데이터를 삭제
            store.replaceState({
                items: [],
                nextIdx: 1,
                selectedColor: '',
                selectedCategory: '맛집',
                token: null,
                userEmail: null
            });
        }
    });
}

const store = new Vuex.Store({
  state: {
      items: [],
      nextIdx: 1,
      selectedColor: '',
      selectedCategory: '맛집',
      token: getToken(), // 로그인 상태를 유지
      userEmail: getToken() ? decodeJWT(getToken()).sub : null, // 로그인 상태를 유지
  },
  mutations: {
      ADD_ITEM(state, { name, address = '', business = "", category = "" }) {
          state.items.push({
              name: name,
              address: address,
              business: business,
              category: category,
              idx: state.nextIdx++
          });
      },
      DELETE_ITEM(state, id) {
          state.items = state.items.filter(item => item.idx !== id);
      },
      UPDATE_ITEMS(state, newItems) {
          state.items = newItems;
      },
      RESET_LISTSTATE(state) {
        state.items = [];
        state.nextIdx = 0;
      },
      setColor(state, color) {
          state.selectedColor = color;
      },
      setCategory(state, category) {
          state.selectedCategory = category;
      },
      setToken(state, token) {
          state.token = token;
          state.userEmail = token ? decodeJWT(token).sub : null;
      },
      clearAuth(state) {
          state.token = null;
          state.userEmail = null;
      },
      resetStateExceptAuth(state) {
          // 로그인 상태를 제외한 나머지 상태 초기화
          state.items = [];
          state.nextIdx = 1;
          state.selectedColor = '';
          state.selectedCategory = '맛집';
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
          commit('setColor', color);
      },
      updateCategory({ commit }, category) {
          commit('setCategory', category);
      },
      resetListState({ commit }) {
        commit('RESET_LISTSTATE');
      },
      initializeAuth({ commit }) {
          const token = getToken();
          if (token) {
              try {
                  const decodedToken = decodeJWT(token);
                  const now = Date.now() / 1000;

                  if (decodedToken.exp && decodedToken.exp > now) {
                      commit('setToken', token);
                  } else {
                      localStorage.removeItem('token');
                      commit('clearAuth');
                  }
              } catch (error) {
                  console.error('Invalid token:', error);
                  localStorage.removeItem('token');
                  commit('clearAuth');
              }
          }
      },
      logout({ commit }) {
          commit('clearAuth');
          localStorage.removeItem('token');
      },
      resetState({ commit }) {
          commit('resetStateExceptAuth');
      }
  },
  getters: {
      items: state => state.items,
      filteredItems: state => {
          return state.items.filter(item => item.category === state.selectedCategory);
      },
      selectedColor: state => state.selectedColor,
      selectedCategory: state => state.selectedCategory,
      isAuthenticated: state => !!state.token,
      getUserEmail: state => state.userEmail,
  },
  plugins: [
      createPersistedState(),
      resetStatePlugin // 이 플러그인은 로그아웃 시 상태를 완전히 초기화
  ]
});

export default store;
