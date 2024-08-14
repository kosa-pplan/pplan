<script>
export default {
  name: "appHeader",
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated; // Vuex 스토어에서 로그인 상태를 가져옴
    }
  },
  methods: {
    logout() {
      console.log("click");
      this.$store.dispatch('logout'); // Vuex 스토어의 로그아웃 액션 호출
      window.location.href = '/'; // '/' 경로로 이동하면서 새로고침
    },
    goToHome() {
      this.$store.dispatch('resetState'); // 로그인 상태를 제외한 모든 상태를 초기화
      window.location.href = '/'; // '/' 경로로 이동하면서 새로고침
    }
  }
}
</script>

<template>
  <div class="header">
    <img @click="goToHome" src="@/assets/logo.png" alt="logo" class="logo"/>
    <div class="nav-button">
      <router-link to="/review" class="board-button">여행리뷰</router-link>

      <!-- 로그인된 경우 마이페이지와 로그아웃 버튼을 표시하고, 그렇지 않으면 로그인과 회원가입 버튼을 표시 -->
      <template v-if="isLoggedIn">
        <router-link to="/mypage/mycourse" class="mypage-button">마이페이지</router-link>
        <button @click="logout" class="logout-button">로그아웃</button>
      </template>
      <template v-else>
        <router-link to="/login" class="login-button">로그인</router-link>
        <router-link to="/sign-up" class="signup-button">회원가입</router-link>
      </template>
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

.logo:hover {
  cursor: pointer;
}

.nav-button {
  display: flex;
  gap: 10px;
  position: absolute;
  top: 20px;
  right: 20px;
}
@import url('https://fonts.googleapis.com/css2?family=Nanum+Barun+Gothic:wght@400;700&display=swap');

.board-button, .login-button, .signup-button, .mypage-button, .logout-button {
  background-color: #2C3E50;
  border: none;
  color: white;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
  text-decoration: none;
  font-family: 'Nanum Barun Gothic', sans-serif;
  font-weight: 900;
}

.board-button:hover, .login-button:hover, .signup-button:hover, .mypage-button:hover, .logout-button:hover {
  background-color: #1a252f;
}
</style>
