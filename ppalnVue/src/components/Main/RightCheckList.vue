<template>
  <div class="all">
    <div class="top" style="height:10%;">
      <h3>Choice List</h3>
    </div>
    <div class="btndiv" style="height: 80%;">
      <draggable v-model="localButtons" group="buttons" direction="vertical" :animation="200" ghost-class="ghost" drag-class="dragging" @start="onDragStart" @end="onDragEnd" class="button-container">
        <transition-group name="flip-list" tag="div">
          <div v-for="button in localButtons" :key="button.idx" class="button" @click="openModal(button)">
            {{ button.name }}
            <span class="remove-button" @click.stop="confirmDelete(button.idx)">X</span>
          </div>
        </transition-group>
      </draggable>
    </div>
    
   

      




    <div class="bottom" style="height: 10%;">
      <button class="button2" @click="handleButtonClick">경로 보기</button>
    </div>

    <!-- 경로 보기 모달 컴포넌트 -->
    <ModalCompo v-if="modalType === 'directions'" @close="showModal = false, modalType=''" class="test">
        <h2>경로</h2>
        <MapModalCompo :message="directions" :email="this.email" @close="showModal = false, modalType=''"/>
    </ModalCompo>
    <ModalCompo v-if="modalType === 'button'" @close="showModal = false, modalType=''" class="test">
        <h2>장소 정보</h2>
        <div class="modal-body">
          <p>분류: {{ selectedButton.category }}</p>
          <p>장소 이름: {{ selectedButton.name }}</p>
          <p>주소: {{ selectedButton.address }}</p>
        </div>
        
        <div id="map" style="width: 100%; height: 300px;"></div>
    </ModalCompo>

    <!-- 삭제 확인 모달 컴포넌트 -->
    <ConfirmDeleteModal class="test" v-if="showConfirmDeleteModal" @confirm="deleteButton" @close="showConfirmDeleteModal = false" />
  </div>
</template>

<script>
import draggable from 'vuedraggable';
import ModalCompo from './ModalCompo.vue';
import MapModalCompo from './MapModalCompo.vue';
import ConfirmDeleteModal from './ConfirmDeleteModal.vue'; // 추가된 모달 컴포넌트



import {convertAllAddressesToCoordinates, fetchDirections} from '@/services/mapService'

// import axios from 'axios';
/* eslint-disable */
export default {
  components: {
    draggable,
    ModalCompo,
    MapModalCompo,
    ConfirmDeleteModal // 모달 컴포넌트 등록
  },
  created() {
    this.$store.dispatch('initializeAuth'); // Vuex 스토어에서 인증 상태를 초기화합니다.
  },
  computed: {
    userEmail() {
      return this.$store.getters.getUserEmail; // Vuex 스토어에서 userEmail을 가져옴
    },
    localButtons: {
      get() {
        return this.$store.getters.items;
      },
      set(value) {
        this.$store.dispatch('updateItems', value);
      }
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated; // Vuex에서 인증 상태 가져오기
    }
  },
  data() {
    return {
      newItem: '', // 입력 필드의 값
      showModal: false,
      showConfirmDeleteModal: false, // 삭제 확인 모달 표시 여부
      buttonToDelete: null, // 삭제할 버튼의 ID 저장
      directions: '',
      modalType: '',
      selectedButton: {}, // 선택된 버튼 저장
      email:''
    };
  },
  methods: {
    async convertAddressToCoordinates(address) {
  const API_URL = 'https://dapi.kakao.com/v2/local/search/address.json';
  console.log("api테스트");
  const REST_API_KEY = process.env.VUE_APP_API_key;
  console.log(REST_API_KEY);
  
  try {
    const response = await fetch(API_URL + `?query=${encodeURIComponent(address)}`, {
      method: 'GET',
      headers: {
        Authorization: `KakaoAK ${REST_API_KEY}`
      }
    });
    
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const data = await response.json();
    const { x, y } = data.documents[0]?.address || {};
    return { lat: parseFloat(y), lon: parseFloat(x) };
  } catch (error) {
    console.error('Error fetching coordinates:', error);
    throw error;
  }
},
    async loadMap() {
      const coordinates = await this.convertAddressToCoordinates(this.selectedButton.address); // API 키를 입력하세요.
      if (coordinates) {
        const { lat, lon } = coordinates;

        // Kakao Maps API 로드 확인
        if (!window.kakao) {
          console.error('Kakao Maps API is not loaded.');
          return;
        }

        // 지도 생성
        const container = document.getElementById('map');
        const options = {
          center: new kakao.maps.LatLng(lat, lon),
          level: 3
        };
        const map = new kakao.maps.Map(container, options);

        // 마커 추가
        const markerPosition = new kakao.maps.LatLng(lat, lon);
        new kakao.maps.Marker({
          position: markerPosition,
          map: map
        });

        map.setCenter(markerPosition);
      } else {
        console.error('주소 변환 실패');
      }
    },
  
    
    onDragStart() {
      // 드래그 시작 시 처리할 로직
    },
    onDragEnd() {
      // 드래그가 끝난 후 버튼 순서를 Vuex 스토어에 업데이트
      this.localButtons = this.localButtons.slice(); // 트리거링하기 위해 배열을 새로 할당
      console.log(this.localButtons);
    },
    confirmDelete(id) {
      this.buttonToDelete = id;
      this.showConfirmDeleteModal = true;
    },
    deleteButton() {
      if (this.buttonToDelete !== null) {
        this.$store.dispatch('deleteItem', this.buttonToDelete); // Vuex 액션을 호출하여 아이템을 삭제합니다
        this.buttonToDelete = null;
        this.showConfirmDeleteModal = false; // 모달을 닫습니다
      }
    },
    removeButton(id) {
      this.$store.dispatch('deleteItem', id); // Vuex 액션을 호출하여 아이템을 삭제합니다
    },
    openModal(button) {
      this.selectedButton = button;
      this.modalType = 'button'; // 버튼 클릭 모달을 열기 위해 modalType 설정
      this.showModal = true;
      this.loadMap();
    },
    
    async handleButtonClick() {
      this.email = this.userEmail
      if(this.userEmail===null){
        this.$router.push('/login');
      }else{
        if(this.localButtons.length<2){
          console.log(this.userEmail)
         alert("경로를 더 추가해주세요")
        }else{
          const updatedButtons = await convertAllAddressesToCoordinates(this.localButtons);
          console.log(updatedButtons)
          if(updatedButtons==="주소변환 실패"){
            alert("주소가 잘못되었습니다")
          }else{
            this.localButtons = updatedButtons;
            this.directions = await fetchDirections(this.localButtons);
            console.log(this.directions)
            if(this.directions==="경로 찾기 실패"){
              alert("경로 찾기 실패")
            }else{
              this.modalType = 'directions';
              this.showModal = true;
            }
          }
        }
      }

    //주소 불러와서 경로 찍기
    // const testbuttons = {}
    //   // user id 불러오기 테스트
    //   try {
    //     const response = await axios.get('http://localhost:8080/api/course/userid', {
    //       params: { id: 123 } // 예시로 ID 1을 사용
    //     });
    //     const course = response.data;

    //     for(let i =0;i<5;i++){
    //     let propertyName = `placeDTO${i + 1}`;
    
    // // Access the property using bracket notation
    // if (course[0][propertyName]!=null) {
    //   console.log("dkdkdkdk")
    //     // Your logic here
    //     testbuttons[i] = course[0][propertyName]
    // }
    //   }
        
    //   } catch (error) {
    //     console.error('Error fetching course data:', error);
    //   }

      
    //   console.log(testbuttons)
    //   this.directions = testbuttons



      ////////////////////////////////////////////////////////////////////////////////////////


      //좋아요 누른거 가져오기
      // try {
      //   const response = await axios.get('http://localhost:8080/api/course/all', {
      //     params: { id: 123 } // 예시로 ID 1을 사용
      //   });
      //   const course = response.data;
      //   console.log(course)
      // } catch (error) {
      //   console.error('Error fetching course data:', error);
      // }

      
      
    },
  },
};
</script>

<style scoped>
.btndiv {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
}

.button-container {
  display: flex;
  flex-direction: column; /* 세로로 정렬 */
  width: 180px;
}
.place-button {
  background-color: #2C3E50;
  border: none;
  color: white;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
  text-decoration: none;
}
.button {
  padding: 10px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 5px;
  color: black;
  cursor: move;
  text-align: center;
  transition: margin 0.3s ease; /* 간격 조정 애니메이션 */
  margin-bottom: 10px; /* 기본 간격 설정 */
  position: relative; /* 상대 위치 설정 */
}

.button:last-child {
  margin-bottom: 0; /* 마지막 버튼의 간격은 제거 */
}

.remove-button {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  font-size: 14px;
  color: #ff0000;
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.dragging {
  opacity: 0.8;
}

.button:first-child {
  margin-top: 20px; /* 처음 버튼만 원하는 만큼의 값으로 조정 */
}

.bottom {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
  align-items: center; /* 세로 가운데 정렬 */
}

.top {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
  align-items: center; /* 세로 가운데 정렬 */
}
.all {
  height: 100%;
  width: 80%;
  border: 15px solid rgba(57, 167, 142, 0.3); /* 테두리 색상 및 투명도 설정 */
  box-sizing: border-box;
  display: inline-block;
}

.button2 {
  width: 60%;
  padding: 10px;
  margin-bottom: 10px;
  border: none;
  border-radius: 5px;
  background-color: #2C3E50;;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: 0.2s;
}

.button2:hover {
  background-color: cadetblue;
  opacity: 80%;
}

.modal-body {
  margin: 20px 0;
  font-size: 16px;
  color: #555;
  text-align: left; /* 왼쪽 정렬 */
}

.test{
animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}




</style>
