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
        <button v-if="this.check" @click="viewReview">리뷰 보기</button>
      </div>
      <div>
        <button v-if="!this.check" @click="writeReview">리뷰 작성</button>
      </div>
    </div>
  </template>
  
  <script>
  
  import {drawRoute } from '@/services/mapService'
  
  export default {
    components: {
    },
    data() {
      return {
        map: null,
        jsonData: null,
        showConfirmModal: false, // 저장 확인 모달 표시 여부
        startIcon: require('@/assets/redmarker.png'), // 출발지 아이콘 이미지 경로
        endIcon: require('@/assets/bluemarker.png'), // 경유지 아이콘 이미지 경로
        waypointsIcon: require('@/assets/blackmarker.png') // 목적지 아이콘 이미지 경로
        
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
      locationdata:{
        
      },

    },
    computed: {
      localButtons: {
        get() {
          return this.$store.getters.items;
        }
      }
    },
    mounted() {
      console.log(this.check)
      console.log(this.courseId)
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
      viewReview(){
        this.$router.push(`/review/detail/${this.courseId}`);
      }
    }
  }
  </script>
  
  <style scoped>
  /* 필요한 경우 스타일을 추가하세요 */
  </style>
  