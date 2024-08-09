<template>
  <div class="middle_main">
    <form @submit.prevent="register">
      <div>
        <label for="email">이메일</label>
        <div class="email-group">
          <input type="text" v-model="form.emailLocal" required placeholder="이메일" />
          <span>@</span>
          <input type="text" v-model="form.emailDomain" required />
          <select v-model="form.emailDomain" @change="updateEmailDomain">
            <option value="">직접입력</option>
            <option value="gmail.com">gmail.com</option>
            <option value="naver.com">naver.com</option>
            <option value="daum.net">daum.net</option>
            <option value="daum.net">hanmail.net</option>
          </select>
        </div>
        <button type="button">중복 확인</button>
        <p v-if="message">{{ message }}</p>
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input type="password" v-model="form.pwd" required />
      </div>
      <div>
        <label for="phone_number">전화번호</label>
        <input type="text" v-model="form.phone_number" required placeholder="- 없이 입력해주세요." />
      </div>
      <div>
        <label for="nickname">닉네임</label>
        <input type="text" v-model="form.nickname" required />
      </div>
      <button type="submit">가입</button>
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
        phone_number: "",
        nickname: ""
      },
      message: "",
      isModalVisible: false,
      modalMessage: ""
    };
  },

  methods: {
    async register() {
      const email = `${this.form.emailLocal}@${this.form.emailDomain}`;
      try {
        const response = await axios.post("http://localhost:8080/sign-up", {
          email: email,
          pwd: this.form.pwd,
          phone_number: this.form.phone_number,
          nickname: this.form.nickname
        });
        this.message = "";
        this.modalMessage = "회원 가입이 완료되었습니다.";
        this.isModalVisible = true;
        console.log(response.data);
      } catch (error) {
        this.message = "회원 가입 실패: " + (error.response && error.response.data.message ? error.response.data.message : error.message);
      }
    },
    updateEmailDomain(event) {
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

p {
  text-align: center;
  color: #333;
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
