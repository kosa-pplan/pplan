<template>
  <div class="middle_main">
    <form @submit.prevent="login">
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
          <span>@</span>
          <input 
            type="text" 
            v-model="form.emailDomain" 
            @input="resetLoginError" 
            required 
            placeholder="도메인"
          />
          <select v-model="form.emailDomain" @change="updateEmailDomain">
            <option value="">직접입력</option>
            <option value="gmail.com">gmail.com</option>
            <option value="naver.com">naver.com</option>
            <option value="daum.net">daum.net</option>
            <option value="hanmail.net">hanmail.net</option>
          </select>
        </div>
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input type="password" v-model="form.pwd" @input="resetLoginError" required />
      </div>
      <button type="submit">로그인</button>
      <p v-if="loginError" class="error">{{ loginError }}</p>
    </form>

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
      loginError: "",  
      isModalVisible: false,
      modalMessage: ""
    };
  },

  methods: {
    updateEmailDomain(event) {
      if (event.target.value !== 'custom') {
        this.form.emailDomain = event.target.value;
      } else {
        this.form.emailDomain = '';
      }
    },

    resetLoginError() {
      this.loginError = "";  // 입력할 때마다 에러 메시지 초기화
    },

    async login() {
      this.loginError = ""; 

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

        if (response.data) {
          this.modalMessage = "로그인 성공!";
          this.isModalVisible = true;
          console.log(response.data);
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

    handleModalClose() {
      this.isModalVisible = false;
      this.$router.push("/home");
    }
  }
}
</script>

<style scoped>
.middle_main {
  flex-direction: column;
}

form {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
}

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

.error {
  color: red;
  margin-top: 5px;
  text-align: center;
}

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
}

.modal button {
  margin-top: 10px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  font-size: 16px;
  cursor: pointer;
}

.modal button:hover {
  background-color: #0056b3;
}
</style>
