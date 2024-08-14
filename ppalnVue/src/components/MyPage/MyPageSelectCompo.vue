<template>
    <div class="div button-container">
      <button @click="goToRoute('/mypage/mycourse')" class="btn">내 경로 보기</button>
      <button @click="goToRoute('/mypage/likecourse')" class="btn">좋아요 경로 보기</button>
      <button @click="goToRoute('/mypage/delete')" class="btn">회원탈퇴</button>
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
  @import url('https://fonts.googleapis.com/css2?family=Nanum+Barun+Gothic:wght@400;700&display=swap');

  .button-container {
    display: flex;
    flex-direction: column;
    align-items: center; /* 버튼들을 가운데 정렬 */
    gap: 10px; /* 버튼들 간의 간격 */

  }
  .btn{
    background-color: #2C3E50;
    border: none;
    color: white;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
    font-size: 14px;
    text-decoration: none;
    width : 150px;
    height: 40px;
    max-width: 100%;
    font-family: 'Nanum Barun Gothic', sans-serif;
    font-weight: 900;
  }
  </style>