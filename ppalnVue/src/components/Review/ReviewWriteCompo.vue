<template>
  <div class="page-container">
    <div class="editor-header">
      <h2 class="title">여행 기록</h2>
      <button @click="fnSave" class="save-button">기록저장</button>
      <button @click="fnList" class="save-button">목록</button>
    </div>
    <div class="editor-content">
      <input
          v-model="title"
          class="title-input"
          placeholder="기록의 시작"
      />
      <vue-editor
          v-model="content"
          class="editor"
          :editorOptions="editorOptions"
          ref="quillEditor"
      />
      <input type="file" multiple @change="handleFileChange"/>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '',
      content: '<p>당신의 여행 기록을 담아주세요</p>',
      editorOptions: {
        modules: {
          toolbar: {
            container: [
              ['bold', 'italic', 'underline', 'strike'],
              ['code-block'],
              [{'header': 1}, {'header': 2}],
              [{'list': 'ordered'}, {'list': 'bullet'}],
              [{'indent': '-1'}, {'indent': '+1'}],
              [{'direction': 'rtl'}],
              [{'header': [1, 2, 3, 4, 5, 6, false]}],
              [{'color': []}, {'background': []}],
              [{'font': []}],
              ['video']
            ]
          }
        }
      },
      images: []
    };
  },
  methods: {
    fnSave() {
      const formData = new FormData();
      formData.append('title', this.title);
      formData.append('contents', this.content);

// 이미지 파일 추가
      for (const image of this.images) {
        formData.append('images', image);
      }

      axios.post('http://localhost:8080/review/write', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            console.log('저장 성공:', response.data);
            alert('저장성공');
            // 저장 성공 후 페이지 이동
            this.$router.push('/review');
          })
          .catch(error => {
            console.error('저장 실패:', error);
            alert('저장실패');
          });
    },
    fnList() {
      this.$router.push('/review');
    },
    handleFileChange(event) {
      this.images = Array.from(event.target.files);
    }
  }
};
</script>
<style scoped>
/* 기존 스타일 */

.page-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding-top: 30px; /* header 높이 */
  padding-bottom: 20px; /* footer 높이 */
  box-sizing: border-box;
  overflow: hidden;
}

.editor-header {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px; /* 상하 패딩 제거 */
  box-sizing: border-box;
  border-bottom: 1px solid #ddd;
  margin-bottom: 5px; /* 제목과 에디터 사이의 마진 최소화 */
}

.title {
  margin: 0;
  font-size: 30px;
  text-align: center;
  flex: 1;
}

.save-button {
  padding: 6px 12px;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  margin-left: auto; /* 버튼을 오른쪽에 배치 */
}

.editor-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 0 5px; /* 상하 패딩 제거 */
  box-sizing: border-box;
  overflow-y: auto; /* 스크롤 가능하게 설정 */
}

.title-input {
  width: 100%;
  padding: 10px;
  font-size: 18px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px; /* 에디터와 제목 입력 필드 사이의 마진 */
}

.editor {
  width: 100%;
  height: calc(100% - 5px);
  border: none; /* 기존의 테두리를 제거하여 깔끔한 디자인 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15); /* 그림자를 더 강조하여 부각 */
  border-radius: 12px; /* 테두리 반경을 늘려서 부드러운 느낌 */
  background-color: #fafafa; /* 배경색을 밝은 회색으로 설정하여 눈의 피로도 감소 */
  overflow-y: auto;
  padding: 10px; /* 에디터 내부에 여백 추가 */
  font-family: Arial, sans-serif; /* 폰트 패밀리 설정 */
  font-size: 16px; /* 폰트 크기 설정 */
  line-height: 1.5; /* 줄 높이를 설정하여 가독성 향상 */
}
</style>