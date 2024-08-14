<template>
  <div class="footer">
    <nav class="footer-nav">
      <div class="footer-line">
        <h6>ⓒ copyright pplan</h6>
        <span class="divider">|</span>
        <a href="https://github.com/kosa-pplan/pplan" target="_blank">Github</a>
      </div>
    </nav>
  </div>
</template>

<script>

export default{

}
</script>

<style scoped>
.footer-nav {
  text-align: center; /* 중앙 정렬 */
  margin-bottom: 10px; /* 아래 여백 */
}

.footer-line {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  gap: 10px; /* 요소 간 간격 */
}

.footer-nav h6 {
  font-size: 14px;
  font-weight: normal;
  color: #2C3E50;
  margin: 0;
}

.footer-nav .divider {
  font-size: 14px; /* 구분자 크기 설정 */
  color: #2C3E50; /* 구분자 색상 설정 */
}

.footer-nav a {
  color: #2C3E50;
  text-decoration: none;
}

.footer-nav a:hover {
  text-decoration: underline;
}

</style>