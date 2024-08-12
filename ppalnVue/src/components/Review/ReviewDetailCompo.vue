<template>
  <div class="page-container">
    <div class="content-wrapper">
      <h1 align="center">{{ title }}</h1>
      <p align="right">기록 일시 {{ regDate }}</p>
      <hr/>
      <!-- HTML 콘텐츠를 안전하게 렌더링 -->
      <div v-html="safeContents"></div>
      <div v-if="reviewImageDTOList.length">
        <h2 align="center">이미지</h2>
        <div class="image-gallery">
          <div v-for="image in reviewImageDTOList" :key="image.imageId" class="image-item">
            <img
                :src="getImageUrl(image.urlPath)"
                :alt="'Image ' + image.imageId"
                class="thumbnail-image"
                @click="openModal(image.urlPath)"
            />
          </div>
        </div>
      </div>
      <!--  비로그인시 좋아요 버튼 안보이게 해야함.-->
      <div>
        <button
            :class="['btn_like', { 'on': isLiked }]"
            @click="toggleLike">
        </button>
      </div>
      <!-- Modal for enlarged image -->
      <div v-if="showModal" class="modal" @click="closeModal">
        <img :src="getImageUrl(enlargedImageUrl)" class="modal-image"/>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import DOMPurify from 'dompurify'; // DOMPurify를 사용하여 HTML을 정화

export default {
  data() {
    return {
      id: null,
      title: '',
      contents: '', // HTML 콘텐츠
      reviewImageDTOList: [],
      regDate: '',
      showModal: false,
      isLiked: false,
      enlargedImageUrl: '',
      userId: 2,              // ID를 1로 고정
      userEmail: 'test@naver.com', // 이메일을 고정
    };
  },
  mounted() {
    this.id = this.$route.params.id;
    this.checkedLikeStatus();
    this.fetchData();
  },
  methods: {
    fetchData() {
      const url = `http://localhost:8080/review/${this.id}`;
      axios.get(url)
          .then(response => {
            const data = response.data;
            this.title = data.title;
            this.contents = DOMPurify.sanitize(data.contents); // HTML 콘텐츠 정화
            this.reviewImageDTOList = data.reviewImageDTOList;
            this.regDate = data.regDate;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    async checkedLikeStatus() {
      try {
        const response = await axios.get('http://localhost:8080/share/isLiked', {
          params: {
            reviewId: this.id,
            userId: this.userId,
          }
        });
        this.isLiked = response.data; // 서버에서 받은 좋아요 상태를 업데이트
      } catch (error) {
        console.log('Error checking like status', error);
      }
    },
    toggleLike() {
      const url = `http://localhost:8080/share/toggle`;
      const payload = {
        reviewId: this.id,
        userId: this.userId,
        userEmail: this.userEmail
      };

      axios.post(url, payload)
          .then(() => {
            this.isLiked = !this.isLiked; // 상태 토글
          })
          .catch(error => {
            console.error('Error toggling like:', error);
          });
    },
    getImageUrl(path) {
      return `http://localhost:8080/imgs/${path}`;
    },
    openModal(imageUrl) {
      this.enlargedImageUrl = imageUrl;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.enlargedImageUrl = '';
    },
  },
  computed: {
    safeContents() {
      return this.contents; // 정화된 HTML 콘텐츠 반환
    }
  }
};
</script>

<style scoped>
.page-container {
  height: 100vh; /* 전체 화면 높이 */
  overflow-y: auto; /* 세로 스크롤 허용 */
  padding: 16px; /* 여백 설정 */
  box-sizing: border-box; /* 패딩과 테두리가 전체 너비와 높이에 포함되도록 설정 */
  display: flex; /* Flexbox 활성화 */
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: baseline; /* 수직 중앙 정렬 */
}

.content-wrapper {
  width: 70%; /* 너비를 70%로 설정 */
  max-width: 1200px; /* 최대 너비를 설정 (옵션) */
  background-color: rgba(255, 255, 240, 0.7); /* 은은한 투명도 70%의 아이보리 색 */
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
}

.image-gallery {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* 수평 중앙 정렬 */
}

.image-item {
  margin: 10px;
  border: 1px solid #ddd;
  padding: 5px;
  border-radius: 5px;
}

.thumbnail-image {
  width: 150px;
  height: 150px;
  object-fit: cover;
  cursor: pointer;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-image {
  max-width: 90%;
  max-height: 90%;
  cursor: pointer;
}
.btn_like {
  width: 50px;
  height: 50px;
  background: url(https://umings.github.io/images/i_like_off.png) no-repeat center / 50px;
  cursor: pointer;
  border: 0;
  font-size: 0;
  margin: 50px auto;
  display: block;
}

.btn_like.on {
  background: url(https://umings.github.io/images/i_like_on.png) no-repeat center / 50px;
  animation: beating .5s 1 alternate;
}

@keyframes beating {
  0% { transform: scale(1); }
  40% { transform: scale(1.25); }
  70% { transform: scale(0.9); }
  100% { transform: scale(1); }
}
</style>
