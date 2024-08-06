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
    <LeftCategorySection/>
    <LeftPlaceChoice/>
    <CenterPlaceList/>
    <RightCheckList/>
  </div>
</template>

<style scoped>

</style>