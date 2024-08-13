<!--
@Author KyeongMin
-->
<template>
  <div class="page-container">
    <p class="no-data-message" v-if="cards.length === 0">데이터가 없습니다.</p>
    <div class="review_list">
      <!-- 데이터가 없는 경우 -->
      <!-- 카드 리스트를 반복하여 표시 -->
      <router-link v-for="(card, index) in displayedCards" :key="index" :to="`/review/detail/${card.id}`"
                   class="card-link">
        <div class="card">
          <!-- 첫 번째 썸네일 이미지가 있을 때 표시 -->
          <img v-if="card.reviewImageDTOList && card.reviewImageDTOList.length > 0"
               :src="`http://localhost:8080/imgs/${card.reviewImageDTOList[0].urlPath}`"
               alt="Card Image"
               class="card-image">

          <!-- 썸네일이 없을 때 기본 이미지 표시 -->
          <img v-else
               src="https://via.placeholder.com/150"
               alt="Default Card Image"
               class="card-image">

          <!-- 카드 제목 표시 -->
          <h3>{{ card.title }}</h3>
          <!-- 카드 등록 날짜 표시 -->
          <p>{{ card.regDate }}</p>

          <!-- 하트 아이콘 표시 -->
          <div class="like-container">
            <img
                :src="'https://cdn-icons-png.flaticon.com/512/833/833472.png'"
                class="like-icon"
            >
            <span class="like-count">{{ card.likeCount }}</span>
          </div>
        </div>
      </router-link>
    </div>

    <div class="button-container">
      <button @click="loadMore" v-if="displayedCards.length < totalCards" class="load-more-button">더보기</button>
    </div>
  </div>

</template>

<script>
export default {
  name: 'CardBoard',
  data() {
    return {
      cards: [], // 모든 카드 데이터를 저장할 배열
      displayedCards: [], // 현재 화면에 표시될 카드들
      cardsToShow: 6, // 한 번에 보여줄 카드 수
      currentPage: 0, // 현재 페이지 번호
      totalCards: 0, // 전체 카드 수
      loading: false, // 데이터 로딩 상태
    };
  },
  computed: {
    userEmail() {
      // Vuex 스토어에서 사용자 이메일을 가져오는 계산된 속성
      return this.$store.getters.getUserEmail;
    }
  },
  methods: {
    async fetchCards(page, size) {
      this.loading = true;
      try {
        const response = await this.$axios.get("/review/list", {
          params: {
            page: page,
            size: size,
          },
        });
        console.log('Total Cards from Server:', response.headers['x-total-count']);
        this.totalCards = parseInt(response.headers['x-total-count'], 10) || 0;
        this.cards = [...this.cards, ...response.data]; // 기존 cards 배열에 추가
        this.updateDisplayCards();
      } catch (error) {
        console.error('카드 데이터를 가져오는 중 오류 발생:', error);
        if (error.response && error.response.status === 401) {
          this.$store.dispatch('logout');
          this.$router.push('/login');
        }
      } finally {
        this.loading = false;
      }
    },
    updateDisplayCards() {
      const start = this.currentPage * this.cardsToShow;
      const end = start + this.cardsToShow;
      this.displayedCards = this.cards.slice(0, end);
      console.log('Displayed Cards:', this.displayedCards.length, 'out of', this.totalCards);
    },
    loadMore() {
      if (this.displayedCards.length < this.totalCards) {
        this.currentPage++;
        this.fetchCards(this.currentPage, this.cardsToShow);
      }
    }
  },
  created() {
    this.$store.dispatch('initializeAuth');
    this.fetchCards(this.currentPage, this.cardsToShow);
  }
};
</script><style scoped>
.page-container {
  padding: 16px;
}

.review_list {
  display: grid; /* 카드 목록을 그리드 레이아웃으로 표시 */
  grid-template-columns: repeat(3, 1fr); /* 3열 그리드 설정 */
  gap: 16px; /* 카드 간 간격 설정 */
  justify-content: center; /* 카드들을 가운데 정렬 */
}

.card {
  border: 1px solid #ddd; /* 카드 테두리 색상 */
  border-radius: 8px; /* 카드 모서리 둥글게 설정 */
  padding: 16px; /* 카드 내부 여백 설정 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 카드에 그림자 추가 */
  display: flex; /* 플렉스 레이아웃으로 카드 내용 배치 */
  flex-direction: column; /* 세로 방향으로 배치 */
  align-items: center; /* 카드 내용 가운데 정렬 */
  margin: 16px; /* 카드 주변 여백 설정 */
}

.card-image {
  width: 150px; /* 이미지 너비 고정 */
  height: 150px; /* 이미지 높이 고정 */
  object-fit: cover; /* 이미지 비율 유지 및 영역에 맞추기 */
  border-radius: 8px 8px 0 0;
}

.card h3 {
  margin: 16px 0 8px; /* 제목의 상하 여백 설정 */
}

.card p {
  margin: 0; /* 등록 날짜의 여백 제거 */
}

.button-container {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 (선택 사항) */
  height: 100px; /* 부모 컨테이너의 높이를 조정해야 할 수도 있습니다 */
}

.load-more-button {
  background-color: #2C3E50;
  border: none;
  color: white;
  padding: 8px 15px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
  text-decoration: none;
}

.card-link {
  text-decoration: none; /* 링크 기본 스타일 제거 */
}

.like-icon {
  width: 16px; /* 기존 크기보다 작게 조정 */
  height: 16px; /* 기존 크기보다 작게 조정 */
  cursor: pointer;
  transition: color 0.3s, transform 0.3s;
}

.like-icon {
  width: 20px; /* 하트 아이콘 크기 조정 */
  height: 20px;
  cursor: pointer;
  margin-right: 6px;
  transition: transform 0.3s ease;
}

.like-icon:hover {
  transform: scale(1.2);
}

.like-count {
  font-size: 18px; /* 숫자 폰트 크기 조정 */
  font-weight: bold; /* 숫자 폰트 굵게 */
  color: #333; /* 숫자 색상 설정 */
}

.no-data-message {
  text-align: center; /* 메시지 가운데 정렬 */
  color: #555; /* 메시지 색상 설정 */
  font-size: 1.2em; /* 메시지 폰트 크기 설정 */
  padding: 20px; /* 메시지 패딩 설정 */
}
</style>
