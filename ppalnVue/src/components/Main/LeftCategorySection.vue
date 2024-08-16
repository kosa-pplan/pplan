<script>
import {mapActions} from "vuex";

export default {
  data() {
    return {
      category: [
        { text: "놀거리" },
        { text: "맛집" },
        { text: "카페" }
      ],
      val_sel: "맛집"
    };
  },
  computed:{
    isAuthenticated() {
      return this.$store.getters.isAuthenticated; // Vuex에서 인증 상태 가져오기
    }
  },
  watch: {
    val_sel(newCategory) {
      this.updateCategory(newCategory); // Vuex 스토어에 선택된 카테고리를 업데이트
    }
  },
  methods :{
    ...mapActions(['updateCategory']) // Vuex 액션을 매핑하여 사용
  }
}
</script>

<template>
  <!-- 카테고리 선택_이푸름 -->
  <div v-if="isAuthenticated">
    <select v-model="val_sel" class="choice">  <!-- val_sel에 데이터 연결 -->
      <option v-for="(item, idx) in category" v-bind:key="idx"> <!-- 배열의 각 항목을 반복하여 드롭다운 메뉴의 항목을 만든다?? -->
        {{ item.text }} <!-- 현재 선택된 항목을 화면에 표시 -->
      </option>
    </select>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Barun+Gothic:wght@400;700&display=swap');

.choice{
  font-family: 'Nanum Barun Gothic', sans-serif; /* 글꼴 설정 */
  font-size: 16px; /* 글꼴 크기 설정 */
  padding: 7px 15px; /* 내부 패딩 추가로 크기 확대 */
  border-radius: 2px; /* 모서리를 둥글게 */
  font-weight: 900; /* 글꼴 설정 */
  background-color: rgba(229, 241, 255, 0.8);
}

.choice option{
  font-family: 'Nanum Barun Gothic', sans-serif;
  font-size: 16px; /* 글꼴 크기 설정 */
  padding: 5px 10px; /* 옵션 내부 패딩 */
  font-weight: 900; /* 글꼴 설정 */
  background-color: white;
  color : black;
}

</style>