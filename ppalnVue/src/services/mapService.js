import axios from "axios";

//주소 입력하면 위경도로 변환
async function convertAddressToCoordinates(address) {
    const API_URL = 'https://dapi.kakao.com/v2/local/search/address.json';
    const REST_API_KEY = process.env.VUE_APP_API_key; // 실제 API 키로 변경

    try {
      const response = await axios.get(API_URL, {
        params: {
          query: address,
        },
        headers: {
          Authorization: `KakaoAK ${REST_API_KEY}`,
        },
      });

      // 응답 데이터에서 좌표를 추출
      const { x, y } = response.data.documents[0].address;

      return { lat: parseFloat(y), lon: parseFloat(x) };
    } catch (error) {
      console.error('Error fetching coordinates:', error);
      throw error;
    }
  }

  //버튼들의 주소를 위경도로 변환
  export async function convertAllAddressesToCoordinates(buttons) {
    try {
      const promises = buttons.map(button =>
        convertAddressToCoordinates(button.address)
          .then(converted => ({
            ...button,
            lat: converted.lat,
            lon: converted.lon
          }))
          .catch(() => {
            console.log("주소 에러 발생:", button.address);
            throw new Error("주소변환 실패"); // 에러를 발생시켜 Promise.allSettled에서 감지 가능하게 만듦
          })
      );
  
      const results = await Promise.allSettled(promises);
  
      // 모든 프라미스의 상태를 체크
      const hasError = results.some(result => result.status === 'rejected');
  
      if (hasError) {
        console.error("하나 이상의 주소 변환이 실패했습니다.");
        return "주소변환 실패"; // 에러가 있는 경우 "ㅁㄴ" 반환
      }
  
      // 모든 변환이 성공했을 경우, 변환된 버튼 배열을 반환
      const updatedButtons = results.map(result => result.value);
      console.log(updatedButtons);
      return updatedButtons;
  
    } catch (error) {
      console.error('Error converting all addresses to coordinates:', error);
      return "주소변환 실패"; // 전반적인 오류 발생 시 "ㅁㄴ" 반환
    }
  }

 //경로 그리기
 export async function fetchDirections(buttons) {

    const API_URL = 'https://apis-navi.kakaomobility.com/v1/waypoints/directions';
    const REST_API_KEY = process.env.VUE_APP_API_key; // 여기에 실제 API 키를 입력하세요
    
    try {
      const response = await axios.post(
        API_URL,
        {
          origin: {
            x: buttons[0].lon,
            y: buttons[0].lat,
          },
          destination: {
            x: buttons[buttons.length - 1].lon,
            y: buttons[buttons.length - 1].lat,
          },
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
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `KakaoAK ${REST_API_KEY}`,
          },
        },
      );
      
      const directions = JSON.stringify(response.data, null, 2);
      return directions
      
    } catch (error) {
      console.error('Error fetching directions:', error);
      return "경로 찾기 실패"
    }
  }