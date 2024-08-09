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
        pink:[{ name: '이태원', location:'서울특별시 종로구 혜화동1' },
          { name: '홍대', location:'서울특별시 종로구 혜화동2' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동3' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동4' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동5' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동6' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동7' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동8' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동9' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동10' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동11' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동12' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동13' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동14' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동15' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동16' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동17' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동18' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동19' },
          { name: '가로수길', location:'서울특별시 종로구 혜화동20' },
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
          location: place.location,
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
