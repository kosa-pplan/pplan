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
  <!-- 카테고리 선택 -->
  <div v-if="isAuthenticated">
    <select v-model="val_sel">  <!-- val_sel에 데이터 연결 -->
      <option v-for="(item, idx) in category" v-bind:key="idx"> <!-- 배열의 각 항목을 반복하여 드롭다운 메뉴의 항목을 만든다?? -->
        {{ item.text }} <!-- 현재 선택된 항목을 화면에 표시 -->
      </option>
    </select>
  </div>
</template>

<style scoped>
</style>