<template>
    <div>
      <div id="map" style="width: 100%; height: 400px;">
        <!-- 지도는 이 div에 렌더링 됩니다. -->
      </div>
      <div>
        <button @click="saveMap">저장</button>
      </div>
    </div>
  </template>
  
  <script>
  import html2canvas from 'html2canvas';
  import jsonData from '@/components/Main/test.json';
  
  export default {
    data() {
      return {
        map: null,
      };
    },
    mounted() {
      if (window.kakao && window.kakao.maps) {
        this.initMap();
      } else {
        const script = document.createElement('script');
        /* global kakao */
        script.onload = () => kakao.maps.load(() => this.initMap());
        script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=86f3b8a4c013ae2ddba3b540b16bc569&libraries=services';
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
        this.drawRoute();
      },
      async drawRoute() {
        function extractLatLngFromJson() {
          const linePaths = [];
          console.log('JSON Data:', jsonData);
  
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
        }
  
        const linePath = extractLatLngFromJson();
  
        if (linePath.length > 0) {
          const polyline = new kakao.maps.Polyline({
            path: linePath,
            strokeWeight: 5,
            strokeColor: '#FF0000',
            strokeOpacity: 0.7,
            strokeStyle: 'solid',
          });
  
          polyline.setMap(this.map);
  
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
  
          const bounds = new kakao.maps.LatLngBounds();
          linePath.forEach((point) => bounds.extend(point));
          this.map.setBounds(bounds);
        } else {
          console.error('경로가 정의되지 않았습니다.');
        }
      },
      saveMap() {
        html2canvas(document.getElementById('map')).then((canvas) => {
          const link = document.createElement('a');
          link.href = canvas.toDataURL('image/png');
          link.download = 'map.png';
          link.click();
        });
      },
    },
  };
  </script>
  
  <style scoped>
  /* 필요한 경우 스타일을 추가하세요 */
  </style>
  