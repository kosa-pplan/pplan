<template>
  <div class="middle_main">
    <form @submit.prevent="register">
      <div>
        <label for="email">이메일</label>
        <div class="email-group">
          <input 
            type="text" 
            v-model="form.emailLocal" 
            @input="handleEmailInput" 
            required 
            placeholder="이메일" 
          />
          <span>@</span>
          <input 
            type="text" 
            v-model="form.emailDomain" 
            @input="handleEmailInput" 
            required 
          />
          <select v-model="form.emailDomain" @change="updateEmailDomain">
            <option value="">직접입력</option>
            <option value="gmail.com">gmail.com</option>
            <option value="naver.com">naver.com</option>
            <option value="daum.net">daum.net</option>
            <option value="hanmail.net">hanmail.net</option>
          </select>
        </div>
        <div class="duplicate_section">
          <button class="duplicate_button" type="button" @click="checkEmailDuplicate">중복 확인</button>
          <p v-if="emailError">{{ emailError }}</p>
          <p v-if="emailCheckMessage && !isEmailChecked">{{ emailCheckMessage }}</p>
          <p v-if="!emailCheckMessage && !isEmailChecked && form.emailLocal && form.emailDomain" class="warning">이메일 중복 확인이 필요합니다.</p>
        </div>
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input type="password" v-model="form.pwd" required />
      </div>
      <div>
        <label for="check_password">비밀번호 확인</label>
        <input type="password" v-model="form.confirmPwd" required />
        <p v-if="passwordMismatchError" class="error">{{ passwordMismatchError }}</p>
      </div>
      <div>
        <label for="nickname">닉네임</label>
        <input type="text" v-model="form.nickname" @input="handleNicknameInput" required />
      </div>
      <div class="duplicate_section">
        <button class="duplicate_button" type="button" @click="checkNicknameDuplicate">중복 확인</button>
        <p v-if="nicknameError">{{ nicknameError }}</p>
        <p v-if="nicknameCheckMessage && !isnicknameChecked">{{ nicknameCheckMessage }}</p>
        <p v-if="!nicknameCheckMessage && !isnicknameChecked && form.nickname" class="warning">닉네임 중복 확인이 필요합니다.</p>
      </div>
      <button type="submit">가입</button>
      <p v-if="formError" class="error">{{ formError }}</p>
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
  data() {
    return {
      form: {
        emailLocal: "",
        emailDomain: "",
        pwd: "",
        confirmPwd: "",
        nickname: ""
      },
      emailError: "",  
      emailCheckMessage: "",
      isEmailChecked: false,  
      nicknameError: "",  
      nicknameCheckMessage: "",
      isnicknameChecked: false,  
      passwordMismatchError: "",  
      formError: "",  
      isModalVisible: false,
      modalMessage: ""
    };
  },

  watch: {
    'form.confirmPwd': function() {
      this.checkPasswordMatch();
    },
    'form.pwd': function() {
      this.checkPasswordMatch();
    }
  },

  methods: {
    handleEmailInput() {
      this.resetEmailCheck();
    },

    handleNicknameInput() {
      this.resetNicknameCheck();
    },

    resetEmailCheck() {
      this.emailCheckMessage = "";
      this.emailError = "";
      this.isEmailChecked = false;
    },

    async checkEmailDuplicate() {
      if (!this.form.emailLocal || !this.form.emailDomain) {
        this.emailError = "이메일을 입력해주세요.";
        return;
      }

      const email = `${this.form.emailLocal}@${this.form.emailDomain}`;
      try {
        const response = await axios.get("http://localhost:8080/check-email", {
          params: { email }
        });
        if (response.data) {
          this.emailCheckMessage = "이미 사용된 이메일입니다.";
          this.isEmailChecked = false;
        } else {
          this.emailCheckMessage = "사용 가능한 이메일입니다.";
          this.isEmailChecked = true;
          this.emailError = ""; // 사용 가능한 이메일일 때는 에러 메시지 초기화
        }
      } catch (error) {
        this.emailCheckMessage = "오류가 발생했습니다. 다시 시도해주세요.";
        this.isEmailChecked = false;
      }
    },

    resetNicknameCheck() {
      this.nicknameCheckMessage = "";
      this.nicknameError = "";
      this.isnicknameChecked = false;
    },

    async checkNicknameDuplicate() {
      if (!this.form.nickname) {
        this.nicknameError = "닉네임을 입력해주세요.";
        return;
      }

      try {
        const response = await axios.get("http://localhost:8080/check-nickname", {
          params: { nickname: this.form.nickname }
        });
        if (response.data) {
          this.nicknameCheckMessage = "이미 사용된 닉네임입니다.";
          this.isnicknameChecked = false;
        } else {
          this.nicknameCheckMessage = "사용 가능한 닉네임입니다.";
          this.isnicknameChecked = true;
          this.nicknameError = ""; // 사용 가능한 닉네임일 때는 에러 메시지 초기화
        }
      } catch (error) {
        this.nicknameCheckMessage = "오류가 발생했습니다. 다시 시도해주세요.";
        this.isnicknameChecked = false;
      }
    },

    checkPasswordMatch() {
      if (this.form.pwd && this.form.confirmPwd && this.form.pwd !== this.form.confirmPwd) {
        this.passwordMismatchError = "비밀번호가 일치하지 않습니다.";
      } else {
        this.passwordMismatchError = "";
      }
    },

    async register() {
      this.formError = ""; 

      if (!this.form.emailLocal || !this.form.emailDomain) {
        this.formError = "이메일을 입력해주세요.";
        return;
      }

      if (!this.form.pwd || !this.form.confirmPwd) {
        this.formError = "비밀번호를 입력해주세요.";
        return;
      }

      if (this.passwordMismatchError) {
        this.formError = "비밀번호가 일치하지 않습니다.";
        return;
      }

      if (!this.form.nickname) {
        this.formError = "닉네임을 입력해주세요.";
        return;
      }

      if (!this.isEmailChecked) {
        this.formError = "이메일 중복 확인을 해주세요.";
        return;
      }

      if (!this.isnicknameChecked) {
        this.formError = "닉네임 중복 확인을 해주세요.";
        return;
      }

      const email = `${this.form.emailLocal}@${this.form.emailDomain}`;
      try {
        const response = await axios.post("http://localhost:8080/sign-up", {
          email: email,
          pwd: this.form.pwd,
          nickname: this.form.nickname
        });
        this.formError = "";
        this.modalMessage = "회원 가입이 완료되었습니다.";
        this.isModalVisible = true;
        console.log(response.data);
      } catch (error) {
        this.formError = "회원 가입 실패: " + (error.response && error.response.data.message ? error.response.data.message : error.message);
      }
    },
    
    updateEmailDomain(event) {
      this.resetEmailCheck();  
      if (event.target.value !== 'custom') {
        this.form.emailDomain = event.target.value;
      } else {
        this.form.emailDomain = '';
      }
    },

    handleModalClose() {
      this.isModalVisible = false;
      this.$router.push("/login");
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

.email-group span {
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

.duplicate_button {
  width: 25%;
}

.duplicate_section {
  display: flex;
}

.duplicate_section p {
  margin: 0 auto;
  align-content: center;
}

.error {
  color: red;
  margin-top: 5px;
  text-align: center;
}

.warning {
  color: orange;
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
