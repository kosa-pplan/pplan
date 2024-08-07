<script>
import LeftCategorySection from "@/components/Main/LeftCategorySection.vue";
import LeftPlaceChoice from "@/components/Main/LeftPlaceChoice.vue";
import CenterPlaceList from "@/components/Main/CenterPlaceList.vue";
import RightCheckList from "@/components/Main/RightCheckList.vue";

export default {
  components: {RightCheckList, CenterPlaceList, LeftPlaceChoice, LeftCategorySection}
}
</script>

<template>
  <div class="middle_main">
    <div class="left">
      <LeftPlaceChoice/>
      <LeftCategorySection/>
    </div>
    <div class="center">
      <CenterPlaceList/>
    </div>
    <div class="right">
      <RightCheckList/>
    </div>
  </div>
</template>

<style scoped>

</style>