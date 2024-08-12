<template>
  <div class="middle_main">
  <MyPageSelectCompo class="left"></MyPageSelectCompo>
  <router-view class="right"></router-view>
  </div>
  
</template>

<script>
import MyPageSelectCompo from './MyPageSelectCompo.vue';

export default {
  components: { MyPageSelectCompo },
}
</script>

<style scoped>
.my-page-container {
  display: flex;
  /* 원하는 레이아웃으로 배치하기 위해 Flexbox 사용 */
}

.left{
  border: 1px solid black;
  width: 20%;
  height: 100%;
}
.right{
  border: 1px solid black;
  width: 80%;
  height: 100%;
}
</style>