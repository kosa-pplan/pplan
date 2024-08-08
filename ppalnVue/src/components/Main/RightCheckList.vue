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
      <button @click="handleButtonClick">경로 보기</button>
    </div>

    <!-- 경로 보기 모달 컴포넌트 -->
    <ModalCompo v-if="modalType === 'directions'" @close="showModal = false, modalType=''">
        <h2>경로</h2>
        <MapModalCompo :message="directions"/>
    </ModalCompo>
    <ModalCompo v-if="modalType === 'button'" @close="showModal = false, modalType=''">
        <h2>버튼 정보</h2>
        <p>버튼 이름: {{ selectedButton.name }}</p>
        <p>버튼 주소: {{ selectedButton.address }}</p>
    </ModalCompo>

    <!-- 삭제 확인 모달 컴포넌트 -->
    <ConfirmDeleteModal v-if="showConfirmDeleteModal" @confirm="deleteButton" @close="showConfirmDeleteModal = false" />
  </div>
</template>

<script>
import draggable from 'vuedraggable';
import ModalCompo from './ModalCompo.vue';
import MapModalCompo from './MapModalCompo.vue';
import ConfirmDeleteModal from './ConfirmDeleteModal.vue'; // 추가된 모달 컴포넌트

import axios from 'axios';

export default {
  components: {
    draggable,
    ModalCompo,
    MapModalCompo,
    ConfirmDeleteModal // 모달 컴포넌트 등록
  },
  computed: {
    localButtons: {
      get() {
        return this.$store.getters.items;
      },
      set(value) {
        this.$store.dispatch('updateItems', value);
      }
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
      selectedButton: {} // 선택된 버튼 저장
    };
  },
  methods: {
    
    onDragStart() {
      // 드래그 시작 시 처리할 로직
    },
    onDragEnd() {
      // 드래그가 끝난 후 버튼 순서를 Vuex 스토어에 업데이트
      this.localButtons = this.localButtons.slice(); // 트리거링하기 위해 배열을 새로 할당
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
    //도로명 주소 위경도 변환
    async convertAddressToCoordinates(address) {
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
    },

    async convertAllAddressesToCoordinates() {
      try {
        const promises = this.localButtons.map(button =>
          this.convertAddressToCoordinates(button.address).then(converted => ({
            ...button,
            lat: converted.lat,
            lon: converted.lon
          }))
        );

        const updatedButtons = await Promise.all(promises);

        // 변환된 위도와 경도를 console.log로 출력
        // updatedButtons.forEach(button => {
        //   console.log(`Button ID: ${button.idx}, Name: ${button.name}`);
        //   console.log(`Latitude: ${button.lat}, Longitude: ${button.lon}`);
        // });

        // 변환된 좌표로 localButtons 업데이트
        this.localButtons = updatedButtons;

      } catch (error) {
        console.error('Error converting all addresses to coordinates:', error);
      }
    },

    //경로 검색
    async fetchDirections() {

      const API_URL = 'https://apis-navi.kakaomobility.com/v1/waypoints/directions';
      const REST_API_KEY = process.env.VUE_APP_API_key; // 여기에 실제 API 키를 입력하세요
      
      try {
        const response = await axios.post(
          API_URL,
          {
            origin: {
              x: this.localButtons[0].lon,
              y: this.localButtons[0].lat,
            },
            destination: {
              x: this.localButtons[this.localButtons.length - 1].lon,
              y: this.localButtons[this.localButtons.length - 1].lat,
            },
            waypoints: this.localButtons.slice(1, -1).map(button => ({
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
        this.directions = JSON.stringify(response.data, null, 2);
        // console.log(JSON.stringify(response.data, null, 2))
      } catch (error) {
        console.error('Error fetching directions:', error);
      }
    },
    openModal(button) {
      this.selectedButton = button;
      this.modalType = 'button'; // 버튼 클릭 모달을 열기 위해 modalType 설정
      this.showModal = true;
    },
    async handleButtonClick() {
      if(this.localButtons.length<2){
      console.log()
        alert("경로 추가해주세요")
        console.log(process.env.VUE_APP_API_key)
      }else{
        await this.convertAllAddressesToCoordinates();
        await this.fetchDirections();
      this.modalType = 'directions';
        this.showModal = true;
      }
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
  width: 150px;
}

.button {
  padding: 10px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
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
</style>
