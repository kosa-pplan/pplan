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
  width: 15%;
  height: 100%;
  background-color: rgba(161, 201, 241, 0.3);
}
.right{

  width: 85%;
  height: 100%;
}
</style>