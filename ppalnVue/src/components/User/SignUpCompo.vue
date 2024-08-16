<!-- 김동혁 -->
<template>
  <div class="middle_main">
    <form @submit.prevent="register">
      <!-- 이메일 입력 필드 -->
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
        <!-- 이메일 중복 확인 및 메시지 표시 -->
        <div class="duplicate_section">
          <button class="duplicate_button" type="button" @click="checkEmailDuplicate">중복 확인</button>
          <p v-if="emailError">{{ emailError }}</p>
          <p v-if="emailCheckMessage " style="color: green">{{ emailCheckMessage }}</p>
          <p v-if="!emailCheckMessage && !isEmailChecked && form.emailLocal && form.emailDomain" class="warning">이메일 중복 확인이 필요합니다.</p>
        </div>
      </div>

      <!-- 비밀번호 입력 필드 -->
      <div>
        <label for="password">비밀번호</label>
        <input type="password" v-model="form.pwd" required />
      </div>

      <!-- 비밀번호 확인 필드 -->
      <div>
        <label for="check_password">비밀번호 확인</label>
        <input type="password" v-model="form.confirmPwd" required />
        <p v-if="passwordMismatchError" class="error">{{ passwordMismatchError }}</p>
      </div>

      <!-- 닉네임 입력 필드 -->
      <div>
        <label for="nickname">닉네임</label>
        <input type="text" v-model="form.nickname" @input="handleNicknameInput" required />
      </div>

      <!-- 닉네임 중복 확인 및 메시지 표시 -->
      <div class="duplicate_section">
        <button class="duplicate_button" type="button" @click="checkNicknameDuplicate">중복 확인</button>
        <p v-if="nicknameError">{{ nicknameError }}</p>
        <p v-if="nicknameCheckMessage " style="color: green">{{ nicknameCheckMessage }}</p>
        <p v-if="!nicknameCheckMessage && !isnicknameChecked && form.nickname" class="warning">닉네임 중복 확인이 필요합니다.</p>
      </div>

      <!-- 회원가입 제출 버튼 -->
      <button type="submit">가입</button>
      <p v-if="formError" class="error">{{ formError }}</p>
    </form>

    <!-- 회원가입 완료 모달 -->
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
      // 폼 데이터와 상태 변수들 정의
      form: {
        emailLocal: "",
        emailDomain: "",
        pwd: "",
        confirmPwd: "",
        nickname: ""
      },
      emailError: "", // 이메일 에러 메시지
      emailCheckMessage: "", // 이메일 중복 확인 메시지
      isEmailChecked: false, // 이메일 중복 확인 여부
      nicknameError: "", // 닉네임 에러 메시지
      nicknameCheckMessage: "", // 닉네임 중복 확인 메시지
      isnicknameChecked: false, // 닉네임 중복 확인 여부
      passwordMismatchError: "", // 비밀번호 불일치 에러 메시지
      formError: "", // 폼 제출 에러 메시지
      isModalVisible: false, // 모달 표시 여부
      modalMessage: "" // 모달 메시지
    };
  },

  watch: {
    // 비밀번호와 비밀번호 확인 필드의 변화를 감지하여 일치 여부를 확인
    'form.confirmPwd': function() {
      this.checkPasswordMatch();
    },
    'form.pwd': function() {
      this.checkPasswordMatch();
    }
  },

  methods: {
    // 이메일 입력 시 중복 확인 초기화
    handleEmailInput() {
      this.resetEmailCheck();
    },

    // 닉네임 입력 시 중복 확인 초기화
    handleNicknameInput() {
      this.resetNicknameCheck();
    },

    // 이메일 중복 확인 초기화
    resetEmailCheck() {
      this.emailCheckMessage = "";
      this.emailError = "";
      this.isEmailChecked = false;
    },

    // 이메일 중복 확인
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
        } else if(!response.data) {
          this.emailCheckMessage = "사용 가능한 이메일입니다.";
          this.isEmailChecked = true;
          this.emailError = ""; // 유효한 이메일에 대한 오류 메시지 지우기
        }
      } catch (error) {
        this.emailCheckMessage = "오류가 발생했습니다. 다시 시도해주세요.";
        this.isEmailChecked = false;
      }
    },

    // 닉네임 중복 확인 초기화
    resetNicknameCheck() {
      this.nicknameCheckMessage = "";
      this.nicknameError = "";
      this.isnicknameChecked = false;
    },

    // 닉네임 중복 확인
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
          this.nicknameError = ""; // 유효한 닉네임에 대한 오류 메시지 지우기
        }
      } catch (error) {
        this.nicknameCheckMessage = "오류가 발생했습니다. 다시 시도해주세요.";
        this.isnicknameChecked = false;
      }

    },

    // 비밀번호와 비밀번호 확인의 일치 여부 확인
    checkPasswordMatch() {
      if (this.form.pwd && this.form.confirmPwd && this.form.pwd !== this.form.confirmPwd) {
        this.passwordMismatchError = "비밀번호가 일치하지 않습니다.";
      } else {
        this.passwordMismatchError = "";
      }
    },

    // 회원가입 처리
    async register() {
      this.formError = "";

      // 폼 유효성 검사
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

      // 회원가입 요청
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
    
    // 이메일 도메인 선택 처리
    updateEmailDomain(event) {
      this.resetEmailCheck();
      if (event.target.value !== 'custom') {
        this.form.emailDomain = event.target.value;
      } else {
        this.form.emailDomain = '';
      }
    },

    // 모달 닫기 처리
    handleModalClose() {
      this.isModalVisible = false;
      this.$router.push("/login");
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

.email-group span {
  margin-right: 5px;
  font-weight: bold;
}

/* 폼 필드 스타일 */
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

.duplicate_button {
  width: 25%;
}

/* 중복 확인 섹션 스타일 */
.duplicate_section {
  display: flex;
}

.duplicate_section p {
  margin: 0 auto;
  align-content: center;
}

/* 에러 및 경고 메시지 스타일 */
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
