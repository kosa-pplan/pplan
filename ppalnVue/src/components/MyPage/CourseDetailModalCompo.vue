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
        <button @click="saveMap">저장</button>
      </div>
      <!-- 확인 모달 컴포넌트 -->
      <SaveConfirmModal v-if="showConfirmModal" @confirm="handleSave" @close="showConfirmModal = false" />
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
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
      locationdata:{
        
      }

    },
    computed: {
      localButtons: {
        get() {
          return this.$store.getters.items;
        }
      }
    },
    mounted() {
      console.log("넘어가는지 확인")
      console.log(this.message)
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
      sendData() {
        let newArray = this.localButtons.map(button => {
          return {
            name: button.name,
            address: button.address,
            category: button.category,
            business: button.business
          };
        });
        console.log(newArray);
  
        axios.post('http://localhost:8080/api/course/save', newArray, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        .then(response => {
          this.$emit('close');
          this.showConfirmModal = false; // 모달 닫기
          console.log(response.data);
        })
        .catch(error => {
          console.error('There was an error!', error);
        });
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
      // extractLatLngFromJson(jsonData) {
      //   const linePaths = [];
      //   if (jsonData && jsonData.routes) {
      //     jsonData.routes.forEach((route) => {
      //       route.sections.forEach((section) => {
      //         section.roads.forEach((road) => {
      //           for (let i = 0; i < road.vertexes.length; i += 2) {
      //             const lat = road.vertexes[i + 1];
      //             const lng = road.vertexes[i];
      //             linePaths.push(new kakao.maps.LatLng(lat, lng));
      //           }
      //         });
      //       });
      //     });
      //   } else {
      //     console.error('JSON 데이터가 올바르지 않습니다.');
      //   }
      //   return linePaths;
      // },
      // extractWaypointsFromJson(jsonData) {
      //   const waypoints = [];
      //   if (jsonData && jsonData.routes) {
      //     jsonData.routes.forEach((route) => {
      //       if (route.summary.waypoints) {
      //         route.summary.waypoints.forEach((point) => {
      //           const lat = point.y; // 위도
      //           const lng = point.x; // 경도
      //           waypoints.push(new kakao.maps.LatLng(lat, lng));
      //         });
      //       }
      //     });
      //   } else {
      //     console.error('JSON 데이터에 waypoints가 없습니다.');
      //   }
      //   return waypoints;
      // },
      // async drawRoute() {
      //   const linePath = this.extractLatLngFromJson(this.jsonData); // jsonData를 전달
      //   const waypoints = this.extractWaypointsFromJson(this.jsonData); // jsonData를 전달
  
      //   if (linePath.length > 0) { 
      //     const polyline = new kakao.maps.Polyline({
      //       path: linePath,
      //       strokeWeight: 5,
      //       strokeColor: '#FF0000',
      //       strokeOpacity: 0.7,
      //       strokeStyle: 'solid',
      //     });
  
      //     polyline.setMap(this.map);
  
      //     console.log(this.localButtons[0].name)
      //     // 출발지와 목적지에 마커 추가
      //     new kakao.maps.Marker({
      //       map: this.map,
      //       position: linePath[0],
      //       title: this.localButtons[0].name,
      //       image: new kakao.maps.MarkerImage(this.startIcon, new kakao.maps.Size(28, 40)) // 출발지 아이콘 설정
      //     });
  
      //     new kakao.maps.Marker({
      //       map: this.map,
      //       position: linePath[linePath.length - 1],
      //       title: this.localButtons[this.localButtons.length-1].name,
      //       image: new kakao.maps.MarkerImage(this.endIcon, new kakao.maps.Size(28, 40)) // 도착지 아이콘 설정
      //     })
  
      //     // Waypoint에만 마커 추가
      //     waypoints.forEach((point, index) => {
      //       new kakao.maps.Marker({
      //         map: this.map,
      //         position: point,
      //         title: this.localButtons[index+1].name,
      //         image: new kakao.maps.MarkerImage(this.waypointsIcon, new kakao.maps.Size(28, 40)), // 출발지 아이콘 설정
      //         clickable: true // 마커 클릭 가능하도록 설정 (선택 사항)
      //       });
      //     });
  
      //     const bounds = new kakao.maps.LatLngBounds();
      //     linePath.forEach((point) => bounds.extend(point));
      //     this.map.setBounds(bounds);
      //   } else {
      //     console.error('경로가 정의되지 않았습니다.');
      //   }
      // },
      saveMap() {
        this.showConfirmModal = true; // 확인 모달 열기
      },
      handleSave() {
        this.sendData();
      }
    }
  }
  </script>
  
  <style scoped>
  /* 필요한 경우 스타일을 추가하세요 */
  </style>
  