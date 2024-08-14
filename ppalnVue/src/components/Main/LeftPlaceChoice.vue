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
    ...mapState(['selectedColor']), // Vuex에서 선택된 색상 가져오기
    isAuthenticated() {
      return this.$store.getters.isAuthenticated; // Vuex에서 인증 상태 가져오기
    },
    localButtons: {
      get() {
        return this.$store.getters.items;
      },
      set(value) {
        this.$store.dispatch('updateItems', value);
      }
    },
  },
  methods: {
    ...mapActions(['updateColor']), // Vuex 액션을 매핑하여 사용

    openModal() {
      if (this.isAuthenticated) {
        // 로그인 되어 있으면 모달을 열기
        this.isModalOpen = true;
      } else {
        // 로그인되지 않았으면 로그인 페이지로 리다이렉트
        this.$router.push('/login');
      }
    },
    closeModal() {
      this.isModalOpen = false;
    },
    chooseRandomColor() {
      const randomIndex = Math.floor(Math.random() * this.colors.length);
      const color = this.colors[randomIndex];
      this.updateColor(color);

       this.$store.dispatch('resetListState');
    },
    selectColor() {
      this.closeModal();
    },
  }
};
</script>

<template>
  <aside>
    <button @click="openModal" class="region-select">지역 선택</button>
    <div v-if="isModalOpen" class="modal">
      <div class="modal-content">
        <button class="close-button" @click="$emit('close')">X</button>
        <h2>지역 선택</h2>
        <button @click="chooseRandomColor" class="btn">랜덤 색상 선택</button>
        <div :style="{backgroundColor:selectedColor}" class="selected-color"></div>
        <button @click="selectColor" class="btn">선택</button>
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
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.modal-content {
  background: white;
  padding: 30px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2); /* 그림자 추가 */
  max-width: 300px; /* 최대 너비 설정 */
  width: 100%;
  animation: scaleIn 0.3s ease; /* 모달 콘텐츠 팝업 애니메이션 */
}

@keyframes scaleIn {
  from {
    transform: scale(0.8);
  }
  to {
    transform: scale(1);
  }
}

h2 {
  font-size: 24px;
  color: #2C3E50;
  margin-bottom: 20px;
  font-family: 'Arial', sans-serif; /* 폰트 변경 */
}

.selected-color {
  width: 100px;
  height: 100px;
  margin: 20px auto;
  border: 2px solid #2C3E50; /* 테두리 추가 */
  border-radius: 5px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2); /* 내부 그림자 추가 */
}

.btn{
  background-color: cadetblue;
  border: none;
  color: white;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
  text-decoration: none;
  margin: 10px 0;
  transition: background-color 0.3s ease;
}
.close-button {
  top: 10px;
  right: 10px;
  border: none;
  background: #ff0000;
  color: #fff;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 50%;
}
</style>