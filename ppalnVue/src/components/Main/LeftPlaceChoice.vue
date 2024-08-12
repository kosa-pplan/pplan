<script>
import {mapActions, mapState} from "vuex";

export default {
  data(){
    return {
      isModalOpen: false,
      colors: ['blue', 'pink', 'green', 'orange', 'purple'],
    };
  },
  computed:{
    ...mapState(['selectedColor']) // Vuex에서 선택된 색상 가져오기
  },
  methods: {
    ...mapActions(['updateColor']), // Vuex 액션을 매핑하여 사용

    openModal() {
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },
    chooseRandomColor() {
      const randomIndex = Math.floor(Math.random() * this.colors.length);
      const color = this.colors[randomIndex];
      this.updateColor(color);
    },
    selectColor() {
      this.closeModal();
    }
  }
};
</script>

<template>
  <aside>
    <button @click="openModal" class="region-select">지역 선택</button>
    <div v-if="isModalOpen" class="modal">
      <div class="modal-content">
        <h2>지역 선택</h2>
        <button @click="chooseRandomColor">랜덤 색상 선택</button>
        <div :style="{backgroundColor:selectedColor}" class="selected-color"></div>
        <button @click="selectColor">선택</button>
      </div>
    </div>
    <div>
      선택된 색상 : {{ selectedColor}}
    </div>
  </aside>
</template>

<style scoped>
.region-select{
  background-color: #2C3E50; /* 버튼 배경색 */
  color: white; /* 텍스트 색상 */
  padding: 10px 20px; /* 여백 */
  border: none; /* 테두리 제거 */
  border-radius: 5px;
  cursor: pointer; /* 커서 변경 */
  font-size: 12px; /* 폰트 크기 */
}

.modal {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
}
.selected-color {
  width: 100px;
  height: 100px;
  margin: 10px auto;
}
</style>