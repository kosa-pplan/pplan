<!--
@Author KyeongMin
-->
<template>
  <div class="board-list">
    <h1>게시판</h1>
    <ul>
      <li v-for="item in items" :key="item.id" class="board-item">
        <div class="item-title">
          <!-- router-link를 사용하여 동적 경로 생성 -->
          <router-link :to="{ path: `/review/detail/${item.id}` }">
            {{ item.title }}
          </router-link>
        </div>
        <div class="item-content">{{ item.contents }}</div>
        <div class="item-footer">
          <span class="item-date">{{ formatDate(item.regDate) }}</span>
        </div>
      </li>
    </ul>
    <div v-if="loading" class="loading">로딩 중...</div>
    <div v-if="error" class="error">오류가 발생했습니다: {{ error }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'BoardList',
  data() {
    return {
      items: [],
      loading: true,
      error: null,
      userEmail: 'momo@naver.com' // 로그인된 사용자의 이메일을 직접 설정합니다
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {
        // 이메일을 포함하여 서버에 요청합니다
        const response = await axios.get('http://localhost:8080/myPage/myLikeReview', {
          params: {
            userEmail: this.userEmail // 서버에서 사용자의 이메일로 필터링할 수 있습니다
          }
        });
        this.items = response.data;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    }
  }
};
</script>
<style scoped>
.board-list {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
  color: #333;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.board-item {
  border-bottom: 1px solid #ddd;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.item-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
  color: #007bff;
}

.item-content {
  font-size: 16px;
  color: #555;
}

.item-footer {
  margin-top: 10px;
  font-size: 14px;
  color: #888;
  text-align: right;
}

.loading, .error {
  text-align: center;
  margin-top: 20px;
}
</style>