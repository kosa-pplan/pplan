<template>
  <div class="page-container">
    <div class="review_list">
      <div class="card" v-for="(card, index) in displayedCards" :key="index">
<!--        &lt;!&ndash; 첫 번째 썸네일 이미지 표시 &ndash;&gt;
        <img v-if="card.reviewImageDTOList && card.reviewImageDTOList.length > 0"
             :src="`http://localhost:8080/imgs/${card.reviewImageDTOList[0].urlPath}`"
             alt="Card Image"
             class="card-image">

        &lt;!&ndash; 썸네일이 없을 때의 기본 이미지 &ndash;&gt;
        <img v-else
             src="https://via.placeholder.com/150"
             alt="Default Card Image"
             class="card-image">-->
        <h3>{{ card.title }}</h3>
        <p>{{ card.regDate }}</p>
      </div>
    </div>
    <button @click="loadMore" v-if="displayedCards.length < cards.length" class="load-more-button">더보기</button>
  </div>
</template>

<script>
export default {
  name: 'CardBoard',
  data() {
    return {
      cards: [], // 초기 카드 데이터를 빈 배열로 설정
      displayedCards: [], // 처음에 보여줄 카드를 관리하는 배열
      cardsToShow: 6, // 한번에 보여줄 카드의 수
      currentPage: 0, // 현재 페이지 번호
    };
  },
  created() {
    this.fetchCards(); // 컴포넌트 생성 시 카드 데이터를 가져옴
  },
  methods: {
    async fetchCards() {
      try {
        const response = await this.$axios.get(this.$serverUrl + "/review/list"); // Spring Boot 서버에서 데이터 가져오기
        this.cards = response.data;
        this.loadMore(); // 처음에 몇 개의 카드를 로드
      } catch (error) {
        console.error('Error fetching cards:', error);
      }
    },
    loadMore() {
      const start = this.currentPage * this.cardsToShow;
      const end = start + this.cardsToShow;
      const nextCards = this.cards.slice(start, end);
      if (nextCards.length > 0) {
        this.displayedCards = this.displayedCards.concat(nextCards);
        this.currentPage++;
      }
    }
  }
};
</script>
<style scoped>
.page-container {
  height: 100vh;
  overflow-y: auto;
  padding: 16px;
  box-sizing: border-box;
}

.review_list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
  justify-content: center;
}

.card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 16px;
}

.card-image {
  width: 100%;
  height: auto;
  border-radius: 8px 8px 0 0;
}

.card h3 {
  margin: 16px 0 8px;
}

.card p {
  margin: 0;
}

.load-more-button {
  display: block;
  margin: 16px auto;
  padding: 8px 16px;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
}
</style>
