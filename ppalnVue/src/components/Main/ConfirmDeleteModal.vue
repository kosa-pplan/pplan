<template>
    <div class="modal-overlay" @click.self="$emit('close')">
      <div class="modal-content">
        <h2>삭제 확인</h2>
        <p>정말로 이 버튼을 삭제하시겠습니까?</p>
        <button @click="confirmDelete">확인</button>
        <button @click="$emit('close')">취소</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: ['onConfirm'],
    methods: {
      confirmDelete() {
        this.$emit('confirm');
        this.$emit('close');
      },
    },
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
    align-items: center;
    justify-content: center;
  }
  
  .modal-content {
    background: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  .modal-content button {
    margin: 10px;
  }
  </style>
  