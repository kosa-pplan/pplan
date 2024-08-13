import axios from "axios";
/* eslint-disable */
// 주소를 위경도로 변환
async function convertAddressToCoordinates(address) {
  const API_URL = 'https://dapi.kakao.com/v2/local/search/address.json';
  const REST_API_KEY = process.env.VUE_APP_API_key;

  try {
    const response = await axios.get(API_URL, {
      params: { query: address },
      headers: { Authorization: `KakaoAK ${REST_API_KEY}` },
    });

    const { x, y } = response.data.documents[0]?.address || {};
    return { lat: parseFloat(y), lon: parseFloat(x) };
  } catch (error) {
    console.error('Error fetching coordinates:', error);
    throw error;
  }
}

// 버튼들의 주소를 위경도로 변환
export async function convertAllAddressesToCoordinates(buttons) {
  try {
    const promises = buttons.map(button =>
      convertAddressToCoordinates(button.address)
        .then(converted => ({ ...button, lat: converted.lat, lon: converted.lon }))
        .catch(() => {
          console.log("주소 에러 발생:", button.address);
          return { ...button, lat: null, lon: null }; // 에러 발생 시 null 반환
        })
    );

    const results = await Promise.all(promises);
    const hasError = results.some(button => button.lat === null || button.lon === null);

    if (hasError) {
      console.error("하나 이상의 주소 변환이 실패했습니다.");
      return "주소변환 실패";
    }

    return results;
  } catch (error) {
    console.error('Error converting all addresses to coordinates:', error);
    return "주소변환 실패";
  }
}

// 경로 찾기
export async function fetchDirections(buttons) {
  const API_URL = 'https://apis-navi.kakaomobility.com/v1/waypoints/directions';
  const REST_API_KEY = process.env.VUE_APP_API_key;

  try {
    const response = await axios.post(API_URL, {
      origin: { x: buttons[0].lon, y: buttons[0].lat },
      destination: { x: buttons[buttons.length - 1].lon, y: buttons[buttons.length - 1].lat },
      waypoints: buttons.slice(1, -1).map(button => ({
        name: button.name,
        x: button.lon,
        y: button.lat,
      })),
      priority: 'RECOMMEND',
      car_fuel: 'GASOLINE',
      car_hipass: false,
      alternatives: false,
      road_details: false,
    }, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `KakaoAK ${REST_API_KEY}`,
      },
    });

    return JSON.stringify(response.data, null, 2);
  } catch (error) {
    console.error('Error fetching directions:', error);
    return "경로 찾기 실패";
  }
}

// 지도에 경로 그리기
function extractLatLngFromJson(jsonData) {
  const linePaths = [];
  if (jsonData?.routes) {
    jsonData.routes.forEach(route => {
      route.sections.forEach(section => {
        section.roads.forEach(road => {
          road.vertexes.forEach((vertex, index) => {
            if (index % 2 === 0) { // 경도
              const lng = vertex;
              const lat = road.vertexes[index + 1]; // 위도
              linePaths.push(new kakao.maps.LatLng(lat, lng));
            }
          });
        });
      });
    });
  } else {
    console.error('JSON 데이터가 올바르지 않습니다.');
  }
  return linePaths;
}

function extractWaypointsFromJson(jsonData) {
  const waypoints = [];
  if (jsonData?.routes) {
    jsonData.routes.forEach(route => {
      route.summary.waypoints?.forEach(point => {
        waypoints.push(new kakao.maps.LatLng(point.y, point.x));
      });
    });
  } else {
    console.error('JSON 데이터에 waypoints가 없습니다.');
  }
  return waypoints;
}

export async function drawRoute(map, jsonData, localButtons, startIcon, endIcon, waypointsIcon) {
  const linePath = extractLatLngFromJson(jsonData);
  const waypoints = extractWaypointsFromJson(jsonData);

  if (linePath.length > 0) {
    new kakao.maps.Polyline({
      path: linePath,
      strokeWeight: 5,
      strokeColor: '#FF0000',
      strokeOpacity: 0.7,
      strokeStyle: 'solid',
    }).setMap(map);

    new kakao.maps.Marker({
      map: map,
      position: linePath[0],
      title: localButtons[0].name,
      image: new kakao.maps.MarkerImage(startIcon, new kakao.maps.Size(28, 40))
    });

    new kakao.maps.Marker({
      map: map,
      position: linePath[linePath.length - 1],
      title: localButtons[localButtons.length - 1].name,
      image: new kakao.maps.MarkerImage(endIcon, new kakao.maps.Size(28, 40))
    });

    waypoints.forEach((point, index) => {
      new kakao.maps.Marker({
        map: map,
        position: point,
        title: localButtons[index + 1].name,
        image: new kakao.maps.MarkerImage(waypointsIcon, new kakao.maps.Size(28, 40)),
        clickable: true
      });
    });

    const bounds = new kakao.maps.LatLngBounds();
    linePath.forEach(point => bounds.extend(point));
    map.setBounds(bounds);
  } else {
    console.error('경로가 정의되지 않았습니다.');
  }
}
