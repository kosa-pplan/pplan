<script>
import LeftCategorySection from "@/components/Main/LeftCategorySection.vue";
import LeftPlaceChoice from "@/components/Main/LeftPlaceChoice.vue";
import CenterPlaceList from "@/components/Main/CenterPlaceList.vue";
import RightCheckList from "@/components/Main/RightCheckList.vue";
import { mapState, mapActions } from "vuex";

export default {
  components: { RightCheckList, CenterPlaceList, LeftPlaceChoice, LeftCategorySection },
  computed: {
    ...mapState(['selectedColor', 'selectedCategory']) // Vuex에서 상태 가져오기
  },
  methods: {
    ...mapActions(['updateColor', 'updateCategory']), // Vuex 액션 사용하기
    updateSelectedColor(color) {
      this.updateColor(color); // Vuex의 updateColor 액션 호출
    },
    updateSelectedCategory(category) {
      this.updateCategory(category); // Vuex의 updateCategory 액션 호출
    }
  }
};
</script>

<template>
  <div class="middle_main">
    <div class="left">
      <LeftPlaceChoice @colorSelected="updateSelectedColor"/>
      <LeftCategorySection @categorySelected="updateSelectedCategory"/>
    </div>
    <div class="center">
      <CenterPlaceList :selectedColor="selectedColor"/>
    </div>
    <div class="right">
      <RightCheckList/>
    </div>
  </div>
</template>

<style scoped>
.left{

}

</style>