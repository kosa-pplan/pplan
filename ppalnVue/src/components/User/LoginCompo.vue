<!-- 김동혁 -->
<template>
  <div class="middle_main">
    <!-- 로그인 폼 -->
    <form @submit.prevent="login">
      <!-- 이메일 입력 필드 -->
      <div>
        <label for="email">이메일</label>
        <div class="email-group">
          <input 
            type="text" 
            v-model="form.emailLocal" 
            @input="resetLoginError" 
            required 
            placeholder="이메일" 
          />
          <strong>@</strong>&nbsp;
          <input 
            type="text" 
            v-model="form.emailDomain" 
            @input="resetLoginError" 
            required 
            placeholder="도메인"
          />
          <!-- 이메일 도메인 선택 옵션 -->
          <select v-model="form.emailDomain" @change="updateEmailDomain">
            <option value="">직접입력</option>
            <option value="daum.net">daum.net</option>
            <option value="gmail.com">gmail.com</option>
            <option value="hanmail.net">hanmail.net</option>
            <option value="icloud.com">icloud.com</option>
            <option value="nate.com">nate.com</option>
            <option value="naver.com">naver.com</option>
          </select>
        </div>
      </div>
      <!-- 비밀번호 입력 필드 -->
      <div>
        <label for="password">비밀번호</label>
        <input type="password" v-model="form.pwd" @input="resetLoginError" required />
      </div>
      <!-- 로그인 버튼 -->
      <button type="submit">로그인</button>
      <!-- 로그인 에러 메시지 -->
      <p v-if="loginError" class="error">{{ loginError }}</p>
    </form>

    <!-- 로그인 성공 모달 -->
    <div v-if="isModalVisible" class="modal-overlay">
      <div class="modal">
        <p>{{ modalMessage }}</p>
        <button @click="handleModalClose">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginCompo",
  data() {
    return {
      form: {
        emailLocal: "",
        emailDomain: "",
        pwd: "",
      },
      loginError: "", // 로그인 에러 메시지
      isModalVisible: false, // 모달 표시 여부
      modalMessage: "" // 모달 메시지
    };
  },
  methods: {
    // 이메일 도메인 선택 처리
    updateEmailDomain(event) {
      if (event.target.value !== 'custom') {
        this.form.emailDomain = event.target.value;
      } else {
        this.form.emailDomain = '';
      }
    },
    // 로그인 에러 초기화
    resetLoginError() {
      this.loginError = "";
    },
    // 로그인 처리
    async login() {
      this.loginError = "";

      // 폼 유효성 검사
      if (!this.form.emailLocal || !this.form.emailDomain) {
        this.loginError = "이메일을 입력해주세요.";
        return;
      }

      if (!this.form.pwd) {
        this.loginError = "비밀번호를 입력해주세요.";
        return;
      }

      const email = `${this.form.emailLocal}@${this.form.emailDomain}`;
      try {
        const response = await axios.post("http://localhost:8080/login", {
          email: email,
          pwd: this.form.pwd,
        });

        if (response.data && response.data.token) {
          // JWT 토큰을 localStorage에 저장
          localStorage.setItem("token", response.data.token);
          this.modalMessage = "로그인 성공!";
          this.isModalVisible = true;
          
          // 사용자가 로그인했음을 상위 컴포넌트에 알리기 위해 이벤트 발생
          this.$emit('login-success');
        } else {
          this.loginError = "이메일 또는 비밀번호가 잘못되었습니다.";
        }
      } catch (error) {
        if (error.response && error.response.status === 401) {
          this.loginError = "이메일 또는 비밀번호가 잘못되었습니다.";
        } else {
          this.loginError = "로그인 실패: " + (error.response && error.response.data.message ? error.response.data.message : error.message);
        }
      }
    },
    // 모달 닫기 처리
    handleModalClose() {
      this.isModalVisible = false;
      this.$router.push("/").then(() => {
        window.location.reload();
      });
    }
  }
}
</script>

<style scoped>
/* 메인 컨테이너 스타일 */
.middle_main {
  flex-direction: column;
}

/* 폼 스타일 */
form {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 450px;
}

/* 이메일 입력 그룹 스타일 */
.email-group {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.email-group input[type="text"],
.email-group select {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-right: 5px;
  flex: 1;
}

/* 폼 필드 스타일 */
email-group span {
  margin-right: 5px;
  font-weight: bold;
}

form div {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="email"] {
  display: block;
}

input[type="email"],
input[type="password"],
input[type="text"] {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

/* 버튼 스타일 */
button {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: cadetblue;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: 0.2s;
}

button:hover {
  background-color: cadetblue;
  opacity: 80%;
}

/* 에러 메시지 스타일 */
.error {
  color: red;
  margin-top: 5px;
  text-align: center;
}

/* 모달 오버레이 및 모달 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  max-width: 400px;
  width: 100%;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  animation: fadeIn 0.3s ease;
}

/* 모달 애니메이션 스타일 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* 모달 버튼 스타일 */
.modal button {
  margin-top: 10px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: cadetblue;
  color: white;
  font-size: 16px;
  cursor: pointer;
  
}

.modal button:hover {
  background-color: cadetblue;
}
</style>
