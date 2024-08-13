<!--
@Author KyeongMin
-->
<template>
  <div class="page-container" v-if="isReviewExists">
    
    <div class="content-wrapper">
      <div>
        <img src="@/assets/redmarker.png" alt="redmarker" style="width: 28px; height: 40px;">
        출발지
        <img src="@/assets/bluemarker.png" alt="bluemarker" style="width: 28px; height: 40px;">
        도착지
        <img src="@/assets/blackmarker.png" alt="blackmarker" style="width: 28px; height: 40px;">
        경유지
      </div>
      <div id="map" style="width: 100%; height: 400px;">
        <!-- 지도는 이 div에 렌더링 됩니다. -->
      </div>
      
      <h1 align="center">{{ title }}</h1>
      <p align="right">기록 일시 {{ regDate }}</p>
      <hr/>
      <div v-if="reviewImageDTOList.length">
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
      <!-- HTML 콘텐츠를 안전하게 렌더링 -->
      <div v-html="safeContents"></div>

      <h1>지도 추가{{ courseId }}</h1>

      <!--비로그인시 좋아요 버튼 안나옴-->
      <button
          :class="['btn_like', { 'on': isLiked }]"
          @click="toggleLike">
        <!-- 버튼에 아이콘이나 텍스트 추가 가능 -->
      </button>

      <!-- Modal for enlarged image -->
      <div v-if="showModal" class="modal" @click="closeModal">
        <img :src="getImageUrl(enlargedImageUrl)" class="modal-image"/>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import DOMPurify from 'dompurify'; // HTML 콘텐츠를 안전하게 정화하기 위한 라이브러리
import {drawRoute, convertAllAddressesToCoordinates, fetchDirections} from '@/services/mapService'

export default {
  data() {
    return {
      id: null, // 리뷰의 ID를 저장하는 변수
      title: '', // 리뷰 제목
      contents: '', // 리뷰의 HTML 콘텐츠
      reviewImageDTOList: [], // 리뷰에 포함된 이미지 목록
      courseId: '',
      regDate: '', // 리뷰 등록 날짜
      showModal: false, // 이미지 모달의 표시 여부
      isLiked: false, // 리뷰의 좋아요 상태
      enlargedImageUrl: '', // 확대된 이미지의 URL
      isReviewExists: true, // 리뷰 존재 여부를 확인하는 변수

      startIcon: require('@/assets/redmarker.png'), // 출발지 아이콘 이미지 경로
      endIcon: require('@/assets/bluemarker.png'), // 경유지 아이콘 이미지 경로
      waypointsIcon: require('@/assets/blackmarker.png'), // 목적지 아이콘 이미지 경로
      map: null,
      jsonData: null,
      locationdata:[],
      directions:''

    };
  },
  mounted() {
    //지도 그릴 준비
    
    // try {
    //   this.jsonData = JSON.parse(this.directions);
    // } catch (e) {
    //   console.error('Invalid JSON data:', e);
    //   this.jsonData = {}; // 파싱 실패 시 빈 객체 할당
    // }
    // console.log('Parsed JSON Data:', this.jsonData); // 확인을 위해 콘솔 출력

    
  },
  async created() {
    
    this.$store.dispatch('initializeAuth'); // Vuex 스토어에서 인증 상태를 초기화
    this.id = this.$route.params.id; // URL 파라미터에서 리뷰 ID를 가져옴
    await this.checkReviewExistence(this.id); // 리뷰 존재 여부를 확인
    if (this.isReviewExists) {
      await this.fetchData(); // 리뷰가 존재하면 데이터를 로드
      await this.checkedLikeStatus(); // 좋아요 상태를 확인
      await this.getDirections()
      try {
        this.jsonData = JSON.parse(this.directions);
      } catch (e) {
          console.error('Invalid JSON data:', e);
          this.jsonData = {}; // 파싱 실패 시 빈 객체 할당
        }
    console.log('Parsed JSON Data:', this.jsonData); // 확인을 위해 콘솔 출력
      if (window.kakao && window.kakao.maps) {
        this.initMap();
      } else {
        const script = document.createElement('script');
        /* global kakao */
        script.onload = () => kakao.maps.load(() => this.initMap());
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.VUE_APP_API_key2}&libraries=services`;
        document.head.appendChild(script);
      } 
    }
    
  },
  methods: {
    initMap() {
        const container = document.getElementById('map');
        const options = {
          center: new kakao.maps.LatLng(37.65191, 126.88418), // 경로의 중간 지점
          level: 5,
        };
        this.map = new kakao.maps.Map(container, options);
        
        drawRoute(this.map, this.jsonData, this.locationdata, this.startIcon, this.endIcon, this.waypointsIcon);
      },
    async getDirections(){
      try {
        console.log(parseInt(this.courseId, 10))
        const response = await axios.get('http://localhost:8080/api/course/id', {
          params: { id: parseInt(this.courseId, 10)}
        });
        const course = response.data;
        
        console.log(course[0]);
        
        //주소 변환
        for(let i =0;i<5;i++){
        let propertyName = `placeDTO${i + 1}`;

        // Access the property using bracket notation
        if (course[0][propertyName].business!=='없음') {
          
          // Your logic here
          this.locationdata[i] = course[0][propertyName]
        }
      }
      const updatedButtons = await convertAllAddressesToCoordinates(this.locationdata);
      
      if(updatedButtons==="주소변환 실패"){
        alert("주소가 잘못되었습니다")
      }else{
        this.locationdata = updatedButtons;
        console.log("리뷰 데이터",this.locationdata)
        this.directions = await fetchDirections(this.locationdata);
        console.log(this.directions)
        // this.jsonData = JSON.parse(directions);
      }
      } catch (error) {
        console.error('Error fetching course data:', error);
      }

    },
    async fetchData() {
      // 서버에서 리뷰 데이터를 가져오는 메소드
      const url = `http://localhost:8080/review/${this.id}`;
      try {
        const response = await axios.get(url);
        const data = response.data; // 서버로부터 받은 데이터
        this.title = data.title; // 리뷰 제목
        this.contents = DOMPurify.sanitize(data.contents); // HTML 콘텐츠를 정화하여 저장
        this.reviewImageDTOList = data.reviewImageDTOList; // 리뷰 이미지 목록
        this.regDate = data.regDate; // 리뷰 등록 날짜
        this.courseId = data.courseId;
      } catch (error) {
        console.error('Error fetching data:', error);
        alert('데이터를 가져오는 데 실패했습니다.');
      }
    },
    async checkedLikeStatus() {
      // 사용자의 좋아요 상태를 확인하는 메소드
      const url = 'http://localhost:8080/share/isLiked';
      try {
        const response = await axios.get(url, {
          params: {
            reviewId: this.id,
            userEmail: this.userEmail
          }
        });
        this.isLiked = response.data; // 서버에서 받은 좋아요 상태를 업데이트
      } catch (error) {
        console.log('Error checking like status:', error);
      }
    },
    async checkReviewExistence(reviewId) {
      // 리뷰의 존재 여부를 확인하는 메소드
      const url = 'http://localhost:8080/review/checkReviewCount';
      try {
        const response = await axios.get(url, {
          params: {reviewId}
        });
        if (response.data === 0) {
          this.isReviewExists = false; // 리뷰가 존재하지 않는 경우
          alert('리뷰가 존재하지 않습니다.');
          this.$router.go(-1); // 이전 페이지로 이동
        } else {
          this.isReviewExists = true; // 리뷰가 존재하는 경우
        }
      } catch (error) {
        console.error('Error checking review existence:', error);
        alert('오류가 발생했습니다.');
        this.$router.go(-1); // 오류 발생 시 이전 페이지로 이동
      }
    },
    async toggleLike() {
      // 좋아요 상태를 토글하는 메소드
      if (this.userEmail === null) {
        alert('로그인 해주세요.')
        // Vue.js에서 페이지를 전환하는 방법
        this.$router.push('/login');
      } else {
        const url = 'http://localhost:8080/share/toggle';
        const payload = {
          reviewId: this.id,
          userEmail: this.userEmail,
        }
        try {
          await axios.post(url, payload);
          this.isLiked = !this.isLiked; // 좋아요 상태를 반전
        } catch (error) {
          console.error('Error toggling like:', error);
        }
      }
    },
    getImageUrl(path) {
      // 이미지 경로를 URL로 변환하는 메소드
      return `http://localhost:8080/imgs/${path}`;
    },
    openModal(imageUrl) {
      // 이미지 모달을 여는 메소드
      this.enlargedImageUrl = imageUrl; // 확대할 이미지의 URL 설정
      this.showModal = true; // 모달을 표시
    },
    closeModal() {
      // 이미지 모달을 닫는 메소드
      this.showModal = false; // 모달을 숨김
      this.enlargedImageUrl = ''; // 확대된 이미지 URL 초기화
    },
  },
  computed: {
    safeContents() {
      // 정화된 HTML 콘텐츠를 반환하는 계산된 속성
      return this.contents;
    },
    userEmail() {
      // Vuex 스토어에서 사용자 이메일을 가져오는 계산된 속성
      return this.$store.getters.getUserEmail;
    },
  },
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
  0% {
    transform: scale(1);
  }
  40% {
    transform: scale(1.25);
  }
  70% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
</style>
