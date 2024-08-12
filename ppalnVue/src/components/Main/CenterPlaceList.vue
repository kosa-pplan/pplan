<script>
export default {
  name: 'CenterPaceList',
  props: {
    selectedColor: String
  },

  data() {
    return {
      boxes: new Array(20).fill(null),
      places:{
        blue:[{name:'서울숲',x: 37.544579, y: 127.037697 }, {name: '2'}, '3', '4', '5', '6', '7', '8', '9', '10','11', '12', '13', '14', '15', '16', '17', '18', '19', '20'],
        pink:[{ name: '미소야(구파발역점)', location:'서울특별시 은평구 진관2로 29-21, 드림스퀘어 105호 (진관동)' ,category:"테스트", business:"아무거나"},
          { name: '분식집 막내아들', location:'서울특별시 강북구 오패산로 396-1, 1층 (번동)' ,category:"테스트", business:"아무거나"},
          { name: '미라클', location:'서울특별시 강북구 도봉로87길 11, 지하1층 (수유동)' ,category:"테스트", business:"아무거나"},
          { name: '전주가콩나물국밥전문점 화곡점', location:'서울특별시 양천구 화곡로 96, 1층 (신월동)' ,category:"테스트", business:"아무거나"},
          { name: '목돈칠이(72) 신월점', location:'서울특별시 양천구 신월로 191, 정세빌딩 1층 (신월동)',category:"테스트", business:"아무거나" },
          { name: '젤로디(Gelo:D)', location:'서울특별시 양천구 신정중앙로19길 7, 101호 (신정동, 춘당아네시스)' ,category:"테스트", business:"아무거나"},
          { name: '오타코 등촌역점', location:'서울특별시 양천구 목동중앙북로7가길 4, 정목빌딩2 106호 (목동)' ,category:"테스트", business:"아무거나"},
          { name: '옐로우캔', location:'서울특별시 강북구 삼각산로 158, 송림빌딩 1층 (수유동)',category:"테스트", business:"아무거나" },
          { name: '헬키푸키 문정점', location:'서울특별시 송파구 법원로 128, 문정역SKV1 1층 B131호 (문정동)' ,category:"테스트", business:"아무거나"},
          { name: '갑숙이네', location:'서울특별시 송파구 백제고분로46길 15, 1층 (송파동)',category:"테스트", business:"아무거나" },
          { name: '포시애틀 헬리오시티점', location:'서울특별시 asd송파구 송파대로 345, 근린생활시설1A동 지하1층 B005,B006호 (가락동, 헬리오시티)',category:"테스트", business:"아무거나"},
          { name: '유정아솥뚜껑오겹살', location:'서울특별시 송파구 백제고분로 175, 애큐파빌딩 1층 (잠실동)',category:"테스트", business:"아무거나" },
          { name: '부암동가든', location:'서울특별시 종로구 백석동1가길 43, WAX갤러리 지하1층 (부암동)',category:"테스트", business:"아무거나" },
          { name: '기억나니 생각나니', location:'서울특별시 강동구 구천면로47길 31, 1층 101호 (암사동)',category:"테스트", business:"아무거나" },
          { name: '보승회관 고덕역점', location:'서울특별시 강동구 동남로71길 20-10, 1층 (명일동)',category:"테스트", business:"아무거나" },
          { name: '거기시켜덮밥&간식 강동점', location:'서울특별시 강동구 천중로16길 44, 1층 101호 (천호동)',category:"테스트", business:"아무거나" },
          { name: '송주불냉면', location:'서울특별시 강동구 천중로16길 44, 1층 102호 (천호동)',category:"테스트", business:"아무거나" },
          { name: '돈돼후 야시장 금천점', location:'서울특별시 금천구 시흥대로78길 17, 1층 (독산동)' ,category:"테스트", business:"아무거나"},
          { name: '37.5광진점', location:'서울특별시 광진구 아차산로76가길 5, 1층 (광장동)' ,category:"테스트", business:"아무거나"},
          { name: '북한산큰숲,제빵소', location:'서울특별시 은평구 북한산로 271-3, 2층 (진관동)',category:"테스트", business:"아무거나" },
        ],
      },
      showDice: false, // to control dice visibility
      dice1: '',
      dice2: '',
      dice3: '',
      dice4: '',
      selectedIndex: null
    };
  },
  watch: {
    selectedColor(newColor) {
      if (newColor) {
        this.showDice = true; // 색상이 선택되면 주사위 보이도록 설정
      }
    }
  },
  methods: {
    rollDice() { // 'dice-num' 클래스를 가진 모든 요소를 선택
      const diceNums = document.querySelectorAll('.dice-num'); // 각 주사위 요소를 반복
      let sum = 0; // 주사위 합산을 위함
      diceNums.forEach((dice, index) => { // 0부터 5까지의 랜덤 숫자 생성
        const randomNum = Math.floor(Math.random() * 6); // 랜덤 숫자에 따라 주사위 눈을 설정
        sum += randomNum; // 1부터 6까지의 숫자로 변경하여 합산

        dice.innerHTML = this.getDiceFace(randomNum);
        // 각 주사위 숫자를 data 속성에 저장
        this[`dice${index + 1}`] = randomNum + 1;
      });
      console.log(`sum: ${sum}`);
      this.useDiceSumIndex(sum);
    },
    getDiceFace(num) {
      const dot = '●';
      const faces = [
        '', // 0점
        dot,
        dot + ' ' + dot,
        dot + ' ' + dot + ' ' + dot,
        dot + ' ' + dot + '<br>' + dot + ' ' + dot,
        dot + ' ' + dot + '<br>' + dot + '<br>' + dot + ' ' + dot,
      ];
      return faces[num];
    },
    useDiceSumIndex(sum) {
      const index = (sum % this.boxes.length)-1; // 주사위 합에 따라 인덱스 계산
      this.selectedIndex = index; // 해당 인덱스의 박스 클릭 처리
      this.highlightSelectedBox(index);
    },
    highlightSelectedBox(index) {
      this.selectedIndex = index;
      const place = this.placeSelectedColor[index];
      if (place) {
        const newItem = {
          name: place.name,
          address: place.location,
          category:place.category,
          business:place.business
        };

        // 현재 저장소의 항목 수를 확인합니다.
        const currentItems = this.$store.state.items; // items는 저장소의 항목 리스트라고 가정합니다.
        if (currentItems.length < 5) {
          this.$store.dispatch('addItem', newItem); // Vuex 액션을 호출하여 아이템을 추가합니다
          console.log(`Selected ${place.name} with ${place.location}`);
          this.$emit('placeSelected', place);
        } else {
          console.log('Item limit reached.');
          alert('최대 5개까지 선택 가능합니다.');
        }
      } else {
        console.log(`No place information available for box ${index + 1}`);
      }
    }
  },
  computed :{
    placeSelectedColor(){
      return this.places[this.selectedColor]|| [];
    }
  }
};
</script>

<template>
  <div class="place-container">
    <div class="map-container">
      <div v-for="(box, index) in boxes" :key="index" :class="['clickable-box', { selected: selectedIndex === index }]">
        <span v-if="placeSelectedColor[index]">{{ placeSelectedColor[index].name }}</span>
      </div>
      <img src="@/assets/seoul_map.jpg" alt="Seoul Map" class="map-image"/>

      <!-- 주사위 공간 -->
      <div v-if="showDice" class="dice-button-container">
        <div class="dice-container">
          <div class="dice-num">{{ dice1 }}</div>
          <div class="dice-num">{{ dice2 }}</div>
          <div class="dice-num">{{ dice3 }}</div>
          <div class="dice-num">{{ dice4 }}</div>
        </div>
        <button class="RollDice-btn" @click="rollDice">Roll Dice</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.place-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.map-container {
  margin-top: 10px;
  position: fixed;
  display: grid;
  width: 800px;
  height: 550px;
  grid-template-columns: repeat(6, 1fr); /* 6개의 컬럼 */
  grid-template-rows: repeat(6, 1fr); /* 6개의 행 */
  gap: 0;
}

.map-image {
  position: absolute;
  grid-column: 2 / 6; /* 두 번째부터 다섯 번째 열까지 차지 */
  grid-row: 2 / 6; /* 두 번째부터 다섯 번째 행까지 차지 */
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 1;
  margin-top: 5px;
}

.clickable-box {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(229, 241, 255, 1.0);
  border: 1px solid #A1C9F1;
  cursor: pointer;
  z-index: 2;
}

/* 상단 박스 */
.clickable-box:nth-child(1) {
  grid-column: 1 / 2;
  grid-row: 1;
}

.clickable-box:nth-child(2) {
  grid-column: 2 / 3;
  grid-row: 1;
}

.clickable-box:nth-child(3) {
  grid-column: 3 / 4;
  grid-row: 1;
}

.clickable-box:nth-child(4) {
  grid-column: 4 / 5;
  grid-row: 1;
}

.clickable-box:nth-child(5) {
  grid-column: 5 / 6;
  grid-row: 1;
}

.clickable-box:nth-child(6) {
  grid-column: 6 / 7;
  grid-row: 1;
}

/* 우측 박스 */
.clickable-box:nth-child(7) {
  grid-column: 6 / 7;
  grid-row: 2;
}

.clickable-box:nth-child(8) {
  grid-column: 6 / 7;
  grid-row: 3;
}

.clickable-box:nth-child(9) {
  grid-column: 6 / 7;
  grid-row: 4;
}

.clickable-box:nth-child(10) {
  grid-column: 6 / 7;
  grid-row: 5;
}

.clickable-box:nth-child(11) {
  grid-column: 6 / 7;
  grid-row: 6;
}

/* 하단 박스 */
.clickable-box:nth-child(12) {
  grid-column: 5 / 6;
  grid-row: 6;
}

.clickable-box:nth-child(13) {
  grid-column: 4 / 5;
  grid-row: 6;
}

.clickable-box:nth-child(14) {
  grid-column: 3 / 4;
  grid-row: 6;
}

.clickable-box:nth-child(15) {
  grid-column: 2 / 3;
  grid-row: 6;
}

.clickable-box:nth-child(16) {
  grid-column: 1 / 2;
  grid-row: 6
}

/* 좌측 박스 */
.clickable-box:nth-child(17) {
  grid-column: 1 / 2;
  grid-row: 5;
}

.clickable-box:nth-child(18) {
  grid-column: 1 / 2;
  grid-row: 4;
}

.clickable-box:nth-child(19) {
  grid-column: 1 / 2;
  grid-row: 3;
}

.clickable-box:nth-child(20) {
  grid-column: 1 / 2;
  grid-row: 2;
}


/* 주사위 컨테이너 스타일 */
.dice-button-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
}

.dice-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin: 20px;
}

/* 각 주사위 숫자 스타일 */
.dice-num {
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f4f8;
  border: 2px solid #2C3E50;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-size: 10px;
  font-weight: bold;
  color: #333;
  text-align: center;
}


/* 주사위 돌리기 버튼 스타일 */
.RollDice-btn {
  display: block;
  margin: 0 auto;
  padding: 10px 20px;
  background-color: #2C3E50;
  border: none;
  border-radius: 5px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  z-index: 2;
}

.RollDice-btn:hover {
  background-color: #0056b3;
}

.selected {
  background-color: #39A78E; /* 선택된 박스의 배경색 */
}
</style>
