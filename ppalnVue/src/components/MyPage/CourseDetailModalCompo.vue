<template>
  <div>
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
    <div>
      <button v-if="this.check" @click="viewReview" class="button2">리뷰 보기</button>
      <button v-if="this.check&&!this.like" @click="deleteReview" class="button2">리뷰 삭제</button>

    </div>
    <div>
      <button v-if="!this.check" @click="writeReview" class="button2" style="margin-right: 10px">리뷰 작성</button>
      <button v-if="!this.check" @click="deleteCourse" class="button2">코스 삭제</button>
    </div>
  </div>
</template>

<script>
import {drawRoute} from '@/services/mapService'
import axios from 'axios';

export default {
  components: {},
  data() {
    return {
      map: null,
      jsonData: null,
      showConfirmModal: false, // 저장 확인 모달 표시 여부
      startIcon: require('@/assets/redmarker.png'), // 출발지 아이콘 이미지 경로
      endIcon: require('@/assets/bluemarker.png'), // 경유지 아이콘 이미지 경로
      waypointsIcon: require('@/assets/blackmarker.png'),// 목적지 아이콘 이미지 경로
      reviewData: {},
    };
  },
  props: {
    message: {
      type: String,
      required: true
    },
    check: {
      type: Boolean,
      required: true
    },
    courseId: {
      type: Number,
      required: true
    },
    like: {
      type: Boolean,
      required: true
    },
    locationdata: {},

  },
  computed: {
    localButtons: {
      get() {
        return this.$store.getters.items;
      }
    }
  },
  async mounted() {
    await this.loadReviewData();

    try {
      this.jsonData = JSON.parse(this.message);
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
  },
  methods: {
    async loadReviewData() {
      const courseId = Number(this.courseId); // 숫자로 변환

      if (!isNaN(courseId)) {
        try {
          const response = await axios.get(`/api/reviewId/${courseId}`);
          this.reviewData = response.data;
          console.log("reviewData: ???/???" +this.reviewData);
        } catch (error) {
          console.error(error);
        }
      }else{
        console.error(this.courseId);
      }
    },
    async deleteReview() {
      if (this.reviewData) {
        try {
          await axios.delete(`http://localhost:8080/review/delete/${this.reviewData}`);
          // 성공적으로 삭제된 경우 처리할 로직 (예: 알림 메시지, 목록 갱신 등)
          alert('리뷰가 성공적으로 삭제되었습니다.');
          // 삭제 후 페이지를 리다이렉트하거나 목록을 갱신할 수 있습니다.
          this.$router.push('/review'); // 예를 들어 리뷰 목록 페이지로 이동
        } catch (error) {
          console.error('Error deleting review:', error);
          alert('리뷰 삭제 중 오류가 발생했습니다.');
        }
      }
    },
    async deleteCourse() {
      if (this.courseId) {
        try {
          await axios.delete(`http://localhost:8080/api/delete/${this.courseId}`);
          // 성공적으로 삭제된 경우 처리할 로직 (예: 알림 메시지, 목록 갱신 등)
          alert('코스가 성공적으로 삭제되었습니다.');
          // 삭제 후 페이지를 리다이렉트하거나 목록을 갱신할 수 있습니다.
          this.$router.push('/review'); // 예를 들어 리뷰 목록 페이지로 이동
        } catch (error) {
          console.error('Error deleting review:', error);
          alert('리뷰 삭제 중 오류가 발생했습니다.');
        }
      }
    },
    initMap() {
      const container = document.getElementById('map');
      const options = {
        center: new kakao.maps.LatLng(37.65191, 126.88418), // 경로의 중간 지점
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
      console.log("드로우 전 확인 this.jsonData")
      console.log(this.locationdata)
      console.log("왜 안되냐")
      drawRoute(this.map, this.jsonData, this.locationdata, this.startIcon, this.endIcon, this.waypointsIcon);
    },
    writeReview() {
      this.$router.push(`/review/write/${this.courseId}`); // courseId 값을 경로에 포함
    },
    viewReview() {
      if(this.reviewData){
        this.$router.push(`/review/detail/${this.reviewData}`);
      }
    }
  }
}
</script>

<style scoped>
.button2 {
  width: 20%;
  padding: 10px;
  margin-top: 10px;
  border: none;
  border-radius: 5px;
  background-color: cadetblue;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: 0.2s;
}

.button2:hover {
  background-color: cadetblue;
  opacity: 80%;
}
</style>
  