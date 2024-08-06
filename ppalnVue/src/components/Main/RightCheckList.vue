<template>
  <div class="all">
    <div class="top" style="height:10%;">
      <h3>Choice List</h3>
    </div>
    <div class="btndiv" style="height: 80%;">
      <draggable v-model="buttons" group="buttons" direction="vertical" :animation="200" ghost-class="ghost" drag-class="dragging" @start="onDragStart" @end="onDragEnd" class="button-container">
        <transition-group name="flip-list" tag="div">
          <div v-for="button in buttons" :key="button.id" class="button">
            {{ button.text }}
            <span class="remove-button" @click="confirmDelete(button.id)">X</span>
          </div>
        </transition-group>
      </draggable>
    </div>
    <div class="bottom" style="height: 10%;">
      <button @click="showModal = true">경로 보기</button>
    </div>

    <!-- 경로 보기 모달 컴포넌트 -->
    <ModalCompo v-if="showModal" @close="showModal = false">
      <h2>경로</h2>
      <MapModalCompo />
    </ModalCompo>

    <!-- 삭제 확인 모달 컴포넌트 -->
    <ConfirmDeleteModal v-if="showConfirmDeleteModal" @confirm="deleteButton" @close="showConfirmDeleteModal = false" />
  </div>
</template>

<script>
import draggable from 'vuedraggable';
import ModalCompo from './ModalCompo.vue';
import MapModalCompo from './MapModalCompo.vue';
import ConfirmDeleteModal from './ConfirmDeleteModal.vue'; // 추가된 모달 컴포넌트

export default {
  components: {
    draggable,
    ModalCompo,
    MapModalCompo,
    ConfirmDeleteModal // 모달 컴포넌트 등록
  },
  data() {
    return {
      buttons: [
        { id: 1, text: '버튼 1' },
        { id: 2, text: '버튼 2' },
        { id: 3, text: '버튼 3' },
        { id: 4, text: '버튼 4' },
      ],
      showModal: false,
      showConfirmDeleteModal: false, // 삭제 확인 모달 표시 여부
      buttonToDelete: null, // 삭제할 버튼의 ID 저장
    };
  },
  methods: {
    onDragStart() {
      // 드래그 시작 시 처리할 로직
    },
    onDragEnd() {
      // 드래그가 끝난 후 버튼 순서를 콘솔에 출력
      console.log('현재 버튼 순서:', this.buttons.map((button) => button.text));
    },
    confirmDelete(id) {
      this.buttonToDelete = id;
      this.showConfirmDeleteModal = true;
    },
    deleteButton() {
      if (this.buttonToDelete !== null) {
        this.buttons = this.buttons.filter((button) => button.id !== this.buttonToDelete);
        this.buttonToDelete = null;
      }
    },
    removeButton(id) {
      this.buttons = this.buttons.filter((button) => button.id !== id);
    },
  },
};
</script>

<style scoped>
.btndiv {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
}

.button-container {
  display: flex;
  flex-direction: column; /* 세로로 정렬 */
  width: 200px;
}

.button {
  padding: 10px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  cursor: move;
  text-align: center;
  transition: margin 0.3s ease; /* 간격 조정 애니메이션 */
  margin-bottom: 10px; /* 기본 간격 설정 */
  position: relative; /* 상대 위치 설정 */
}

.button:last-child {
  margin-bottom: 0; /* 마지막 버튼의 간격은 제거 */
}

.remove-button {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  font-size: 14px;
  color: #ff0000;
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.dragging {
  opacity: 0.8;
}

.button:first-child {
  margin-top: 20px; /* 처음 버튼만 원하는 만큼의 값으로 조정 */
}

.bottom {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
  align-items: center; /* 세로 가운데 정렬 */
}

.top {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
  align-items: center; /* 세로 가운데 정렬 */
}

.all {
  border: 15px solid rgba(57, 167, 142, 0.3); /* 테두리 색상 및 투명도 설정 */
  box-sizing: border-box;
}
</style>
