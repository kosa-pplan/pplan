<template>
  <div class="page-container">
    <div class="editor-header">
      <h2 class="title">여행 기록</h2>
      <button @click="saveContent" class="save-button">Save Content</button>
    </div>
    <div class="editor-content">
      <vue-editor
          v-model="content"
          class="editor"
          :editorOptions="editorOptions"
          ref="quillEditor">
      </vue-editor>
    </div>
    <div class="file-upload">
      <input type="file" @change="handleFileUpload" multiple accept="image/*" />
      <div class="file-list">
        <div v-for="(image, index) in images" :key="index" class="file-item">
          <img :src="image.url" alt="Uploaded image" class="uploaded-image" />
          <button @click="removeImage(index)" class="remove-button">Remove</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import { VueEditor } from 'vue2-editor';

export default {
  components: {
    VueEditor
  },
  data() {
    return {
      content: '<p>당신의 여행 기록을 담아주세요</p>',
      editorOptions: {
        modules: {
          toolbar: {
            container: [
              ['bold', 'italic', 'underline', 'strike'],
              ['code-block'],
              [{ 'header': 1 }, { 'header': 2 }],
              [{ 'list': 'ordered'}, { 'list': 'bullet' }],
              [{ 'indent': '-1'}, { 'indent': '+1' }],
              [{ 'direction': 'rtl' }],
              [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
              [{ 'color': [] }, { 'background': [] }],
              [{ 'font': [] }],
              ['image', 'video']
            ],
            handlers: {
              image: this.imageHandler
            }
          }
        }
      },
      imageCount: 0,
      maxImages: 6
    };
  },
  methods: {
    imageHandler() {
      const input = document.createElement('input');
      input.setAttribute('type', 'file');
      input.setAttribute('accept', 'image/*');
      input.click();

      input.onchange = () => {
        const file = input.files[0];
        if (file) {
          const fileType = file.type.split('/')[0];
          const fileSize = file.size / 1024 / 1024; // MB 단위

          if (fileType !== 'image') {
            alert('Only images are allowed.');
            return;
          }

          if (fileSize > 2) { // 2MB 제한
            alert('File size should not exceed 2MB.');
            return;
          }

          if (this.imageCount < this.maxImages) {
            const reader = new FileReader();
            reader.onload = (e) => {
              const range = this.$refs.quillEditor.quill.getSelection();
              this.$refs.quillEditor.quill.insertEmbed(range.index, 'image', e.target.result);
              this.imageCount++;
              console.log(this.imageCount)
            };
            reader.readAsDataURL(file);
          } else {
            alert('You can upload up to 5 images only.');
          }
        }
      };
    },
    saveContent() {
      localStorage.setItem('travelContent', this.content);
      alert('Content saved to local storage!');
    },
    updateImageCount() {
      const contents = this.$refs.quillEditor.quill.getContents();
      let imageCount = 0;
      contents.ops.forEach(op => {
        if (op.insert && op.insert.image) {
          imageCount++;
        }
      });
      this.imageCount = imageCount;
    }
  },
  mounted() {
    this.quill = this.$refs.quillEditor.quill;
    const savedContent = localStorage.getItem('travelContent');
    if (savedContent) {
      this.content = savedContent;
    }

    this.quill.on('text-change', this.updateImageCount);
  }
};
</script>
<style scoped>
/* 전체 HTML 및 body의 높이를 100%로 설정하고 기본 마진을 제거합니다 */
body, html {
  height: 100%;
  margin: 0;
  padding: 0;
}

/* page-container를 flexbox 컨테이너로 설정하여 세로 방향으로 배치합니다 */
.page-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding-top: 30px; /* header 높이 */
  padding-bottom: 20px; /* footer 높이 */
  box-sizing: border-box;
  overflow: hidden;
}

/* editor-header를 flexbox로 설정하여 제목과 버튼을 배치 */
.editor-header {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px; /* 상하 패딩 제거 */
  box-sizing: border-box;
  //background-color: #f8f9fa;
  border-bottom: 1px solid #ddd;
  margin-bottom: 5px; /* 제목과 에디터 사이의 마진 최소화 */
}

/* 제목을 중앙에 정렬 */
.title {
  margin: 0;
  font-size: 30px;
  text-align: center;
  flex: 1;
}

/* save-button 스타일을 정의합니다 */
.save-button {
  padding: 8px 16px;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  margin-left: auto; /* 버튼을 오른쪽에 배치 */
}

/* editor-content를 flex-grow를 사용하여 남은 공간을 차지하게 하고 스크롤 가능하도록 설정합니다 */
.editor-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 0 5px; /* 상하 패딩 제거 */
  box-sizing: border-box;
  overflow-y: auto; /* 스크롤 가능하게 설정 */
}

/* vue-editor 컴포넌트의 스타일을 정의합니다 */
.editor {
  width: 100%;
  height: calc(100% - 5px); /* 에디터 높이를 고정하여 공백 최소화 */
  border: 1px solid #ddd;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow-y: auto; /* 에디터 내부에서 스크롤 가능하도록 설정 */
}
</style>