<template>
  <div class="page-container">
    <div class="review_list">
      <!-- 카드 리스트를 반복하여 표시 -->
      <div class="card" v-for="(card, index) in displayedCards" :key="index">
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
      </div>
    </div>

    <!-- 더보기 버튼 표시: 표시된 카드 수가 전체 카드 수보다 적을 때만 보이게 함 -->
    <button @click="loadMore" v-if="displayedCards.length < cards.length" class="load-more-button">더보기</button>
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
    };
  },
  created() {
    this.fetchCards(); // 컴포넌트가 생성될 때 카드 데이터를 가져옴
  },
  methods: {
    async fetchCards() {
      try {
        // 서버에서 카드 데이터 가져오기
        const response = await this.$axios.get(this.$serverUrl + "/review/list");
        this.cards = response.data; // 서버에서 가져온 카드 데이터를 저장
        this.loadMore(); // 초기 로드 시 카드 일부를 화면에 표시
      } catch (error) {
        console.error('카드 데이터를 가져오는 중 오류 발생:', error);
      }
    },
    loadMore() {
      // 현재 페이지에 맞는 카드 범위 설정
      const start = this.currentPage * this.cardsToShow;
      const end = start + this.cardsToShow;
      const nextCards = this.cards.slice(start, end);
      if (nextCards.length > 0) {
        // 새 카드를 기존 카드 배열에 추가
        this.displayedCards = this.displayedCards.concat(nextCards);
        this.currentPage++; // 페이지 번호 증가
      }
    }
  }
};
</script>

<style scoped>

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
  width: 100%; /* 카드 이미지 너비를 카드 너비에 맞춤 */
  height: auto; /* 이미지 비율 유지 */
  border-radius: 8px 8px 0 0; /* 이미지 상단 모서리 둥글게 설정 */
}

.card h3 {
  margin: 16px 0 8px; /* 제목의 상하 여백 설정 */
}

.card p {
  margin: 0; /* 등록 날짜의 여백 제거 */
}

.load-more-button {
  display: block; /* 버튼을 블록 레벨 요소로 설정 */
  margin: 16px auto; /* 버튼 중앙 정렬 및 여백 설정 */
  padding: 8px 16px; /* 버튼 내부 여백 설정 */
  font-size: 16px; /* 버튼 텍스트 크기 설정 */
  cursor: pointer; /* 버튼에 커서 포인터 표시 */
  border: none; /* 버튼 테두리 제거 */
  border-radius: 4px; /* 버튼 모서리 둥글게 설정 */
  background-color: #007bff; /* 버튼 배경 색상 설정 */
  color: white; /* 버튼 텍스트 색상 설정 */
}
</style>