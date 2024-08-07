<script>
export default {
  name: 'CenterPaceList',
  props: {
    selectedColor: String
  },
  data() {
    return {
      boxes: new Array(20).fill(null), // 4 * 4 겉 테두리 박스
      places:{
        blue:[{name:'서울숲',x: 37.544579, y: 127.037697 }, '2', '3', '4', '5', '6', '7', '8', '9', '10','11', '12', '13', '14', '15', '16', '17', '18', '19', '20'],
        pink:[{ name: '이태원', x: 37.534833, y: 126.994583 },
          { name: '홍대', x: 37.556517, y: 126.922647 },
          { name: '가로수길', x: 37.521543, y: 127.022326 }],
        green : ['서울식물원', '양재천', '광화문 광장'],
        orange: ['경복궁', '창덕궁','덕수궁'],
        purple: ['동대문 디자인 플라자','코엑스','롯데월드타워'],
      }
    };
  },
  methods: {
    handleBoxClick(index) {
      const place = this.placeSelectedColor[index];
      if (place) {
        const newItem = {
          name:  place.name,
          x: place.x,
          y:place.y,
        };
        this.$store.dispatch('addItem', newItem); // Vuex 액션을 호출하여 아이템을 추가합니다
        console.log(`Clicked on ${place.name} with coordinates: (${place.x},${place.y} )`);
        // 필요에 따라 아래 코드로 데이터를 다른 컴포넌트로 전달 가능
        this.$emit('placeSelected', place);
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
      <div v-for="(box, index) in boxes" :key="index" class="clickable-box"
           @click="handleBoxClick(index)">
        <span v-if="placeSelectedColor[index]">{{ placeSelectedColor[index].name }}</span>
      </div>
      <img src="@/assets/seoul_map.jpg" alt="Seoul Map" class="map-image"/>
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
  margin-top: 7px;
  position: relative;
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

</style>
