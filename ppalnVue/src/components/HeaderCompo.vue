<script>
export default {
  name: "appHeader",
}
</script>

<template>
  <div class="header">
      <img src="@/assets/logo.png" alt="logo" class="logo"/>
      <div class="nav-button">
        <button class="board-button">게시판</button>
        <button class="login-button">로그인</button>
        <button class="signup-button">회원가입</button>
      </div>
  </div>
</template>


<style scoped>

.logo-container {
  display: flex;
  align-items: center;
}

.logo {
  height: 80px;
  position: absolute;
  top : 0;
  left: 0;

}

.nav-button {
  display: flex;
  gap: 10px;
  position: absolute;
  top: 20px;
  right: 20px;
}

.board-button, .login-button, .signup-button {
  background-color: #2C3E50;
  border: none;
  color: white;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
}
</style>