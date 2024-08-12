<template>
    <div class="div button-container">
      <button @click="goToRoute('/mypage/mycourse')">내 경로 보기</button>
      <button @click="goToRoute('/mypage/likecourse')">좋아요 경로 보기</button>
      <button @click="goToRoute('/mypage/delete')">회원탈퇴</button>
    </div>
  </template>
  
  <script>
export default {
  methods: {
    goToRoute(route) {
      if (this.$route.path !== route) {
        this.$router.push(route);
      }
    }
  }
}
</script>
  
  <style scoped>
  .button-container {
    display: flex;
    flex-direction: column;
    align-items: center; /* 버튼들을 가운데 정렬 */
    gap: 10px; /* 버튼들 간의 간격 */
    border: 1px solid black;
   
  }
  </style>