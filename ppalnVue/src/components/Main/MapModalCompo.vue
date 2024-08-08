<template>
  <div>
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
import SaveConfirmModal from './SaveConfirmModal.vue'; // 추가된 모달 컴포넌트

export default {
  components: {
    SaveConfirmModal
  },
  data() {
    return {
      map: null,
      jsonData: null,
      showConfirmModal: false // 저장 확인 모달 표시 여부
    };
  },
  props: {
    message: {
      type: String,
      required: true
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
          address: button.address
        };
      });
      console.log(newArray);

      axios.post('http://localhost:8080/api/test', newArray, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
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
      this.drawRoute();
    },
    extractLatLngFromJson(jsonData) {
      const linePaths = [];
      if (jsonData && jsonData.routes) {
        jsonData.routes.forEach((route) => {
          route.sections.forEach((section) => {
            section.roads.forEach((road) => {
              for (let i = 0; i < road.vertexes.length; i += 2) {
                const lat = road.vertexes[i + 1];
                const lng = road.vertexes[i];
                linePaths.push(new kakao.maps.LatLng(lat, lng));
              }
            });
          });
        });
      } else {
        console.error('JSON 데이터가 올바르지 않습니다.');
      }
      return linePaths;
    },
    extractWaypointsFromJson(jsonData) {
      const waypoints = [];
      if (jsonData && jsonData.routes) {
        jsonData.routes.forEach((route) => {
          if (route.summary.waypoints) {
            route.summary.waypoints.forEach((point) => {
              const lat = point.y; // 위도
              const lng = point.x; // 경도
              waypoints.push(new kakao.maps.LatLng(lat, lng));
            });
          }
        });
      } else {
        console.error('JSON 데이터에 waypoints가 없습니다.');
      }
      return waypoints;
    },
    async drawRoute() {
      const linePath = this.extractLatLngFromJson(this.jsonData); // jsonData를 전달
      const waypoints = this.extractWaypointsFromJson(this.jsonData); // jsonData를 전달

      if (linePath.length > 0) { 
        const polyline = new kakao.maps.Polyline({
          path: linePath,
          strokeWeight: 5,
          strokeColor: '#FF0000',
          strokeOpacity: 0.7,
          strokeStyle: 'solid',
        });

        polyline.setMap(this.map);

        // 출발지와 목적지에 마커 추가
        new kakao.maps.Marker({
          map: this.map,
          position: linePath[0],
          title: '출발지',
        });

        new kakao.maps.Marker({
          map: this.map,
          position: linePath[linePath.length - 1],
          title: '목적지',
        });

        // Waypoint에만 마커 추가
        waypoints.forEach((point, index) => {
          new kakao.maps.Marker({
            map: this.map,
            position: point,
            title: `경유지 ${index + 1}`,
            clickable: true // 마커 클릭 가능하도록 설정 (선택 사항)
          });
        });

        const bounds = new kakao.maps.LatLngBounds();
        linePath.forEach((point) => bounds.extend(point));
        this.map.setBounds(bounds);
      } else {
        console.error('경로가 정의되지 않았습니다.');
      }
    },
    saveMap() {
      this.showConfirmModal = true; // 확인 모달 열기
    },
    handleSave() {
      this.sendData();
      this.showConfirmModal = false; // 모달 닫기
    }
  }
}
</script>

<style scoped>
/* 필요한 경우 스타일을 추가하세요 */
</style>
