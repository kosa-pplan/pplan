<script>
import axios from 'axios';
import {mapState} from "vuex";

export default {
  name: 'CenterPaceList',

  data() {
    return {
      boxes: new Array(20).fill({ name: '' }),
      places: [], // 가져온 장소 데이터를 저장
      showDice: false,
      dice1: '',
      dice2: '',
      dice3: '',
      dice4: '',
      selectedIndex: null,
      maxfive : false,
      intervalid : null, //칸 애니메이션 효과 관리 변수
    };
  },
  watch: {
    // 선택된 색상이 변경될 때마다 fetchPlaces를 호출
    selectedColor(newColor) {
      if (newColor) {
        this.showDice = true;
        this.fetchPlaces();
      }
    },
    // 선택된 카테고리가 변경될 때마다 fetchPlaces를 호출
    selectedCategory(newCategory) {
      if (newCategory) {
        this.fetchPlaces();
      }
    },
    // Vuex 스토어의 items 배열을 감시 -> 5개 받은 후 삭제했을 경우를 위함
    '$store.state.items'(newItems) {
      if (newItems.length < 5) {
        this.maxfive = false;
      }
    }
  },
  methods: {
    // API 호출을 통해 장소 데이터를 가져오는 메서드
    async fetchPlaces() {
      try {
        const category = this.selectedCategory;
        const color = this.selectedColor;
        console.log('Fetching places for:', category, color); // 확인용
        const response = await axios.get(`http://localhost:8080/api/games/${category}/${color}`);
        this.places = response.data;

        // 가져온 데이터를 boxes에 바인딩
        this.boxes = this.places.map(place => {
          if (place && place.location_name) {
            return { name: place.location_name };
          } else {
            return { name: 'Unknown' };
          }
        });
      } catch (error) {
        console.error('해당 장소 가져오는데 실패함:', error);
      }
    },
    rollDice() {
      if(this.maxfive) return;

      const diceNums = document.querySelectorAll('.dice-num');
      let sum = 0;
      diceNums.forEach((dice, index) => {
        const randomNum = Math.floor(Math.random() * 6);
        sum += randomNum;
        dice.innerHTML = this.getDiceFace(randomNum);
        this[`dice${index + 1}`] = randomNum + 1;
      });
      console.log(`sum: ${sum}`);
      this.animateSelection(sum);
    },
    getDiceFace(num) {
      const dot = '●';
      const faces = [
        '',
        dot,
        dot + ' ' + dot,
        dot + ' ' + dot + ' ' + dot,
        dot + ' ' + dot + '<br>' + dot + ' ' + dot,
        dot + ' ' + dot + '<br>' + dot + '<br>' + dot + ' ' + dot,
      ];
      return faces[num];
    },
    animateSelection(sum) {
      let currentIndex = this.selectedIndex !== null ? this.selectedIndex : 0;
      const targetIndex = (currentIndex + sum) % this.boxes.length;

      if (this.intervalId) clearInterval(this.intervalId);

      this.intervalId = setInterval(() => {
        this.selectedIndex = currentIndex;

        currentIndex++;
        if (currentIndex >= this.boxes.length) currentIndex = 0;

        if (currentIndex === targetIndex) {
          clearInterval(this.intervalId);
          this.highlightSelectedBox(targetIndex);
        }
      }, 200); // 200ms 간격으로 애니메이션
    },
    highlightSelectedBox(index) {
      if (this.maxfive) {
        return;
      }
      this.selectedIndex = index;
      const place = this.placeSelectedColor[index];
      if (place) {
        const newItem = {
          name: place.name,
          address: place.location,
          category:place.category,
          business:place.business
        };

      if (index >= 0 && index < this.placeSelectedColor.length) {
        const place = this.placeSelectedColor[index];

        // name, address, business 필드 로그 확인
        console.log(`Selected Place:`, place);
        if (place && place.location_name && place.address && place.business) {
          const newItem = {
            location_name: place.location_name,
            address: place.address,
            business: place.business,
          };

          const currentItems = this.$store.state.items;
          if (currentItems.length < 5) {
            this.$store.dispatch('addItem', newItem);
            console.log(`Selected ${place.location_name} with ${place.address} and business ${place.business}`);
            this.$emit('placeSelected', place);

            if (currentItems.length + 1 == 5) {
              this.maxfive = true;
            }
          } else {
            console.log('Item limit reached.');
            alert('최대 5개까지 선택 가능합니다.');
            this.maxfive = true;
          }
        } else {
          console.log(`No place information available for box ${index + 1}`);
        }
      } else {
        console.log(`Index ${index} is out of bounds.`);
      }
    },
    removeItem(index) {
      this.$store.dispatch('removeItem', index);
      if (this.$store.state.items.length < 5) {
        this.maxfive = false; // 아이템이 5개 미만이면 maxfive를 false로 설정
      }
    }
  },
  computed: {
    ...mapState(['selectedColor', 'selectedCategory']),
    placeSelectedColor() {
      // this.places 배열에 값이 정상적으로 들어 있는지 확인
      console.log(this.places);
      return this.places || [];
    }
  }
};
</script>


<template>
  <div class="place-container">
    <div class="map-container">
      <!-- boxes 배열의 각 박스에 데이터를 바인딩 -->
      <div v-for="(box, index) in boxes" :key="index"
           :class="['clickable-box', { selected: selectedIndex === index && !maxfive }]">
        <span v-if="box.name">{{ box.name }}</span>
      </div>
      <img src="@/assets/seoul_map.jpg" alt="Seoul Map" class="map-image"/>

      <!-- 주사위 버튼과 주사위 숫자 표시 -->
      <div v-if="showDice" class="dice-button-container">
        <div class="dice-container">
          <div class="dice-num">{{ dice1 }}</div>
          <div class="dice-num">{{ dice2 }}</div>
          <div class="dice-num">{{ dice3 }}</div>
          <div class="dice-num">{{ dice4 }}</div>
        </div>
        <button class="RollDice-btn" @click="rollDice" :disabled="maxfive">Roll Dice</button>
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
