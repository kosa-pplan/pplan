<!--
@Author KyeongMin
-->
<template>
  <div class="page-container">
    <div class="editor-header">
      <h2 class="title">여행 기록</h2>
    </div>
    <div class="group-button">
      <button @click="fnSave" class="save-button">기록저장</button>
      <button @click="fnList" class="list-button">목록</button>

    </div>
    <br>
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
      <!-- Hidden file input -->
      <input
          type="file"
          multiple
          class="file-input"
          @change="handleFileChange"
          ref="fileInput"
      />
      <!-- Custom button -->
      <button
          class="file-button"
          @click="triggerFileInput"
      >
        이미지 첨부
      </button>
      <div v-if="fileCount > 0" class="file-info">
        <p>선택된 파일 수: {{ fileCount }}</p>
        <ul>
          <li v-for="(file, index) in fileNames" :key="index">{{ file }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '', // 여행 기록의 제목
      content: '<p>당신의 여행 기록을 담아주세요</p>', // 여행 기록의 내용 (기본값)
      editorOptions: {
        modules: {
          toolbar: {
            container: [
              // 툴바 설정: 텍스트 스타일, 리스트, 인덴트, 방향, 헤더, 색상, 폰트, 비디오 삽입
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
      images: [], // 업로드할 이미지 파일들
      courseId: '', // URL 파라미터에서 가져올 courseId
      fileCount: 0,       // 파일 수
      fileNames: []       // 파일 이름들
    };
  },
  computed: {
    userEmail() {
      // Vuex 스토어에서 사용자 이메일을 가져오는 계산된 속성
      return this.$store.getters.getUserEmail;
    }
  },
  methods: {
    async fnSave() {
      // 여행 기록을 서버에 저장하는 메소드
      if (this.images.length === 0) {
        alert('최소 하나의 이미지를 첨부해야 합니다.');
        return; // 이미지가 없으면 저장하지 않음
      }
      const formData = new FormData();
      formData.append('courseId', this.courseId);
      formData.append('title', this.title);
      formData.append('contents', this.content);

      // 이미지 파일 추가
      this.images.forEach(image => {
        formData.append('images', image);
      });

      try {
        const response = await axios.post('http://localhost:8080/review/write', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('저장 성공:', response.data);
        alert('저장 성공');
        this.$router.push('/review'); // 저장 성공 후 리뷰 페이지로 이동
      } catch (error) {
        console.error('저장 실패:', error.response ? error.response.data : error.message);
        alert('저장 실패');
      }
    },
    fnList() {
      // 리뷰 목록 페이지로 이동하는 메소드
      this.$router.push('/review');
    },
    handleFileChange(event) {
      const files = Array.from(event.target.files);
      const maxFileSize = 5 * 1024 * 1024; // 5MB
      const maxFileCount = 3;

      if (files.length > maxFileCount) {
        alert(`최대 ${maxFileCount}개의 파일만 업로드할 수 있습니다.`);
        event.target.value = ''; // Clear the input
        return;
      }

      const validFiles = [];
      const validFileNames = [];
      files.forEach(file => {
        if (file.size > maxFileSize) {
          alert(`파일 ${file.name}은(는) 5MB를 초과합니다.`);
        } else if (!file.type.startsWith('image/')) {
          alert(`파일 ${file.name}은(는) 이미지 파일이 아닙니다.`);
        } else {
          validFiles.push(file); // Add valid files to the array
          validFileNames.push(file.name);
        }
      });

      this.images = validFiles; // Store valid files in the array
      this.fileCount = validFiles.length;
      this.fileNames = validFileNames;

      // Clear the input
      event.target.value = '';
    },
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    async checkAuthorEmail() {
      try {
        const response = await axios.get('http://localhost:8080/review/creator', {
          params: { courseId: this.courseId }
        });

        // 응답에서 DTO를 받아옵니다
        const { userEmail, reviewCheck } = response.data;

        if (reviewCheck === 'YES') {
          alert('이미 작성한 글입니다.');
          this.$router.push('/review'); // 작성 여부가 'YES'일 경우 리뷰 페이지로 이동
        } else if (userEmail !== this.userEmail) {
          alert('접근 권한이 없습니다.');
          this.$router.push('/review'); // 접근 권한이 없을 경우 리뷰 페이지로 이동
        } else {
          // 접근 권한이 있고, 작성 여부가 'NO'인 경우
          // 필요한 추가 작업을 여기서 수행합니다.
        }
      } catch (error) {
        console.error('작성자 이메일 확인 실패:', error);
        alert('오류가 발생했습니다.');
        this.$router.push('/review'); // 오류 발생 시 리뷰 페이지로 이동
      }
    }
  },
  async created() {
    // 컴포넌트가 생성될 때 호출되는 라이프사이클 훅
    this.courseId = this.$route.params.courseId || ''; // URL 파라미터에서 courseId를 가져옴
    this.$store.dispatch('initializeAuth'); // Vuex 스토어에서 인증 상태를 초기화
    await this.checkAuthorEmail(); // 작성자 이메일 확인
  }
};
</script><style scoped>
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
  padding: 0 8px; /* 상하 패딩 제거 */
  box-sizing: border-box;
  margin-bottom: 20px; /* 제목과 에디터 사이의 마진 최소화 */
}

.title {
  margin: 0;
  font-size: 30px;
  text-align: center;
  flex: 1;
}

.group-button {
  display: flex;
  top: 20px;
  right: 20px;
  gap: 10px;
  margin-right: 20px;
  justify-content: flex-end; /* 버튼을 오른쪽으로 정렬 */
}

.save-button, .list-button {
  background-color: #2C3E50;
  border: none;
  color: white;
  padding: 8px 15px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
  text-decoration: none;
}

.save-button:hover, .list-button {
  background-color: #1a252f;
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

/* Hide the default file input */
.file-input {
  display: none;
}

/* Custom button styling */
.file-button {
  display: inline-block;
  padding: 8px 15px; /* 기존 버튼과 유사한 패딩으로 조정 */
  background-color: #2C3E50; /* 기존 버튼과 유사한 배경색으로 조정 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px; /* 기존 버튼과 유사한 폰트 크기로 조정 */
  text-decoration: none;
}

.file-button:hover {
  background-color: #1a252f; /* 기존 버튼과 유사한 호버 색상으로 조정 */
}
/* File info container */
.file-info {
  margin-top: 20px;
  padding: 15px;
  background-color: #f8f9fa; /* Light gray background for better visibility */
  border-radius: 8px;       /* Rounded corners */
  border: 1px solid #ddd;   /* Light border for separation */
}

/* Title for file info */
.file-info p {
  font-size: 16px;
  font-weight: bold;
  color: #333; /* Dark gray color for better readability */
  margin-bottom: 10px;
}

/* List of files */
.file-info ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

/* Individual file items styled as cards */
.file-info li {
  padding: 10px;
  margin-bottom: 10px;
  background-color: #ffffff; /* White background for each file item */
  border-radius: 5px;       /* Rounded corners for file items */
  border: 1px solid #ddd;   /* Light border for file items */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Subtle shadow for a card effect */
  font-size: 14px;          /* Font size for file names */
  color: #555;              /* Slightly darker color for text */
}

/* Hover effect for file items */
.file-info li:hover {
  background-color: #f1f1f1; /* Slightly darker background on hover */
  border-color: #ccc;        /* Darker border color on hover */
  cursor: pointer;           /* Pointer cursor to indicate interactivity */
}
</style>