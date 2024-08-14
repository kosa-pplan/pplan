<template>
  <div class="deleteSection">
    <button class="deleteButton" type="button" @click="deleteAccount">회원 탈퇴</button>
  </div>
</template>

<script>
export default {
  computed: {
    userEmail() {
      return this.$store.getters.getUserEmail; // Vuex 스토어에서 userEmail을 가져옴
    }
  },
  created() {
    this.$store.dispatch("initializeAuth"); // Vuex 스토어에서 인증 상태를 초기화합니다.
  },
  methods: {
    deleteAccount() {
      if (confirm("정말로 회원 탈퇴를 하시겠습니까?")) {
        this.$axios.delete(`/delete-user`, { params: { email: this.userEmail } })
          .then(response => {
            alert("회원 탈퇴가 완료되었습니다.");
            this.$store.dispatch("logout"); // 로그아웃 처리
            this.$router.push("/").then(() => {
              window.location.reload();
            }); // 홈으로 리다이렉트
            console.log(response);
          })
          .catch(error => {
            alert("회원 탈퇴에 실패했습니다.");
            console.error(error);
          });
      }
    }
  }
}
</script>

<style scoped>
.deleteSection {
  align-content: center;
}

.deleteButton {
  border: 0;
  border-radius: 10px;
  background-color: #2C3E50;
  color: white;
  padding: 15px 25px;
  font-size: 16px;
}

.deleteButton:hover {
  cursor: pointer;
}
</style>
