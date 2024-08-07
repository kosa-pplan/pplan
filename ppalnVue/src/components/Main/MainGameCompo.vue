<script>
import LeftCategorySection from "@/components/Main/LeftCategorySection.vue";
import LeftPlaceChoice from "@/components/Main/LeftPlaceChoice.vue";
import CenterPlaceList from "@/components/Main/CenterPlaceList.vue";
import RightCheckList from "@/components/Main/RightCheckList.vue";

export default {
  components: { RightCheckList, CenterPlaceList, LeftPlaceChoice, LeftCategorySection },
  data() {
    return {
      selectedColor: '' // 선택된 색상을 저장
    };
  },
  methods: {
    updateSelectedColor(color) {
      this.selectedColor = color; // selectedColor 상태 업데이트
    }
  }
};
</script>

<template>
  <div class="middle_main">
    <div class="left">
      <LeftPlaceChoice @colorSelected="updateSelectedColor"/>
      <LeftCategorySection/>
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