<template>
  <div class="modal-overlay" @click="$emit('close')">
    <div class="modal-content" @click.stop>
      <h3>저장 확인</h3>
      <p>저장하시겠습니까?</p>
      
      <!-- 제목 입력 필드 -->
      <input 
        v-model="title" 
        type="text" 
        placeholder="제목을 입력하세요" 
      />
      
      <button @click="confirm">확인</button>
      <button @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      title: '' // 입력된 제목을 저장할 데이터 속성
    };
  },
  methods: {
    confirm() {
      if(this.title==="") {
        alert("제목을 입력해 주세요")
      }else{
        this.$emit('confirm', this.title); // 제목을 부모 컴포넌트에 전달
      }
      
    }
  }
}
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
  z-index: 1000; /* 지도 위에 표시되도록 */
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
  z-index: 1001; /* 모달 내용이 오버레이 위에 표시되도록 */
}

button {
  margin: 0 5px;
}
</style>
