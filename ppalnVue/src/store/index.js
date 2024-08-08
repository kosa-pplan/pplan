// Vue와 Vuex를 가져옵니다.
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// 이 모듈에는 상태(state), 뮤테이션(mutations), 액션(actions), 게터(getters)가 포함되어 있습니다.


const store = new Vuex.Store({
    state: {
        items: [],
        nextIdx: 1
      },
      mutations: {
        ADD_ITEM(state, { name, x = 0, y = 0,address='' }) {
            state.items.push({
              name: name,
              x: x,
              y: y,
              address: address,
              idx: state.nextIdx++
            });
          },
        DELETE_ITEM(state, id) {
          state.items = state.items.filter(item => item.idx !== id);
        },
        UPDATE_ITEMS(state, newItems) {
          state.items = newItems;
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
        }
      },
      getters: {
        items: state => state.items
      }
});

// 스토어의 초기 상태를 콘솔에 출력하여 스토어가 올바르게 생성되었는지 확인합니다.
// 여기서는 'countxs' 모듈의 초기 상태를 출력합니다.
console.log('index.js', store.state.countxs);

export default store;