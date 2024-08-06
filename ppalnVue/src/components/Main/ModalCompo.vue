<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <button class="close-button" @click="$emit('close')">X</button>
      <slot></slot> <!-- 모달 내용 삽입을 위한 슬롯 -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModalCompo',
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  position: relative;
  width: 80%;
  max-width: 800px; /* 최대 너비 설정 */
  height: 80%;
}

.close-button {
  position: absolute;
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
