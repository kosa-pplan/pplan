₩<script>
export default {
  name: "appHeader",
}
</script>

<template>
  <div class="header">
    <router-link to="/"><img src="@/assets/logo.png" alt="logo" class="logo"/>
    </router-link>
    <div class="nav-button">
        <router-link to="/review" class="board-button">게시판</router-link>
        <router-link to="/login" class="login-button">로그인</router-link>
        <router-link to="/sign-up" class="signup-button">회원가입</router-link>
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
  text-decoration: none;
}
</style>