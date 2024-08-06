<script>
import CenterPlaceList from './Main/CenterPlaceList.vue';
import LeftCategorySection from './Main/LeftCategorySection.vue';
import LeftPlaceChoice from './Main/LeftPlaceChoice.vue';
import RightCheckList from './Main/RightCheckList.vue';

export default {
  components : {
    RightCheckList,
    LeftCategorySection,
    LeftPlaceChoice,
    CenterPlaceList
  }
}
</script>

<template>

  <div class="middle">
    <div class="left">
      <LeftCategorySection></LeftCategorySection>
      <LeftPlaceChoice></LeftPlaceChoice>
    </div>
    <div class="center">
      <CenterPlaceList></CenterPlaceList>
    </div>
      <RightCheckList class="right"></RightCheckList>
   
      
    
      
  
    
  </div>

</template>

<style scoped>

</style>