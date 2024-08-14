<template>
  <div>
    <div class="container">
  <table class="rwd-table">
    <tbody>
      <tr>
        <th>제목</th>
        <th>상세보기</th>
      </tr>
        <tr v-for="([key, value]) in paginatedData" :key="key">
          <!-- <td>{{ key }}</td> -->
          <td data-th="제목">{{ value.title }}</td>
          <td data-th="상세보기" style="width: 20%; text-align: center;">
            <button @click="handleButtonClick(value)" class="button2">자세히 보기</button>
          </td>
        </tr>
      </tbody>
  </table>
</div>
    <div class="pagination">
      <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">이전</button>
      <span>{{ currentPage }} / {{ totalPages }}</span>
      <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">다음</button>
    </div>

    <div class="modal test" v-if="isModalOpen">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h2>경로</h2>
        <CourseDetailModalCompo :like="this.like" :courseId="this.courseId" :check="this.check" :message="directions" :locationdata="locationdata" @close="showModal = false, modalType=''" @modal-close="handleClose"/>
      </div>
    </div>
  
</div>
</template>
<script>
import axios from 'axios';
import {convertAllAddressesToCoordinates, fetchDirections} from '@/services/mapService'
import CourseDetailModalCompo from './CourseDetailModalCompo.vue';

export default {
  components: {
    CourseDetailModalCompo
  },
  created() {
    this.$store.dispatch('initializeAuth'); // Vuex 스토어에서 인증 상태를 초기화합니다.
  },
  computed: {
    userEmail() {
      return this.$store.getters.getUserEmail; // Vuex 스토어에서 userEmail을 가져옴
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated; // Vuex에서 인증 상태 가져오기
    },
    // 현재 페이지에 해당하는 데이터 범위 계산
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return Object.entries(this.data).slice(start, end);
    },
    // 페이지 네이션 총 페이지 수 계산
    totalPages() {
      return Math.ceil(Object.keys(this.data).length / this.itemsPerPage);
    }
  },
  data() {
    return {
      directions: '',
      data: {},
      isModalOpen: false,
      selectedData: {},
      locationdata: [], // locationdata 추가
      check: false,
      courseId: '',
      currentPage: 1, // 현재 페이지
      itemsPerPage: 8, // 페이지당 아이템 수
      like: false
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    handleClose() {
      this.isModalOpen=false
      this.showModal = false
      this.modalType=''
      this.fetchData();
      // 'close' 이벤트를 수신하고, 모달 창이나 팝업을 닫는 등의 작업을 수행합니다.
      console.log('Close event received');
    },
    async fetchData() {
      try {
        const response = await axios.get('http://localhost:8080/api/course/all', {
          params: { email: this.userEmail }
        });
        const course = response.data;

        this.data = course;
        console.log(this.data);
      } catch (error) {
        console.error('Error fetching course data:', error);
      }
    },
    openModal(value) {
      this.selectedData = value; // 선택된 데이터를 저장
      this.isModalOpen = true; // 모달 열기
    },
    closeModal() {
      this.isModalOpen = false; // 모달 닫기
      this.selectedData = {}; // 선택된 데이터 초기화
      this.locationdata = [];
    },
    async handleButtonClick(value) {
      this.courseId = value.courseId
      if(value.reviewCheck==='YES'){
        this.check = true
      }else{
        this.check = false;
      }
      for(let i =0;i<5;i++){
        let propertyName = `placeDTO${i + 1}`;

        // Access the property using bracket notation
        if (value[propertyName].business!=='없음') {
          console.log(value[propertyName])
          // Your logic here
          this.locationdata[i] = value[propertyName]
        }
      }
      const updatedButtons = await convertAllAddressesToCoordinates(this.locationdata);
      
      if(updatedButtons==="주소변환 실패"){
        alert("주소가 잘못되었습니다")
      }else{
        this.locationdata = updatedButtons;
        this.directions = await fetchDirections(this.locationdata);
        
        
        if(this.directions==="경로 찾기 실패"){
          alert("경로 찾기 실패")
        }else{
          this.isModalOpen = true; // 모달 열기
        }
      }
    },
    // 페이지 변경 메서드
    changePage(page) {
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;
      }
    }
  }
};
</script>
<style scoped>

.table-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 가로 가운데 정렬 */
  height: 100%; /* 전체 화면 높이 */
}

.styled-table {
  margin-top: 20px;
  width: 80%; /* 테이블 너비를 100%로 설정 */
  border-collapse: collapse;
}

.styled-table th, .styled-table td {
  border: 1px solid #dddddd; /* 테두리 색상 */
  text-align: left;
  padding: 8px; /* 여백 */

}

.styled-table th {
  background-color: #f2f2f2; /* 헤더 배경색 */
  text-align: center; /* 텍스트 가운데 정렬 */
}

/* 모달 스타일 */
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  width: 50%; /* 모달 너비 */
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.button2 {
  width: 60%;
  border: none;
  border-radius: 5px;
  background-color: cadetblue;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: 0.2s;
  font-family: 'Nanum Barun Gothic', sans-serif;
  font-weight: 900;
}

.button2:hover {
  background-color: cadetblue;
  opacity: 80%;
}

/* 페이지네이션 스타일 */
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pagination button {
  border: 1px solid #ddd;
  background-color: #f2f2f2;
  color: #333;
  padding: 10px 20px;
  margin: 0 5px;
  cursor: pointer;
  border-radius: 5px;
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.pagination span {
  margin: 0 10px;
}
.test{
animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@import 'https://fonts.googleapis.com/css?family=Open+Sans:600,700';

* {font-family: 'Open Sans', sans-serif;}

.rwd-table {
  
  margin: auto;
  margin-top: 20px;
  min-width: 80%;
  border-collapse: collapse;
}

.rwd-table tr:first-child {
  border-top: none;
  background: #428bca;
  color: #fff;
}

.rwd-table tr {
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  background-color: #f5f9fc;
}

.rwd-table tr:nth-child(odd):not(:first-child) {
  background-color: #ebf3f9;
}

.rwd-table th {
  display: none;
}

.rwd-table td {
  display: block;
}

.rwd-table td:first-child {
  margin-top: .5em;
}

.rwd-table td:last-child {
  margin-bottom: .5em;
}

.rwd-table td:before {
  content: attr(data-th) ": ";
  font-weight: bold;
  width: 120px;
  display: inline-block;
  color: #000;
}

.rwd-table th,
.rwd-table td {
  text-align: left;
}

.rwd-table {
  color: #333;
  border-radius: .4em;
  overflow: hidden;
}

.rwd-table tr {
  border-color: #bfbfbf;
}

.rwd-table th,
.rwd-table td {
  padding: .5em 1em;
}
@media screen and (max-width: 601px) {
  .rwd-table tr:nth-child(2) {
    border-top: none;
  }
}
@media screen and (min-width: 600px) {
  .rwd-table tr:hover:not(:first-child) {
    background-color: #d8e7f3;
  }
  .rwd-table td:before {
    display: none;
  }
  .rwd-table th,
  .rwd-table td {
    display: table-cell;
    padding: .25em .5em;
  }
  .rwd-table th:first-child,
  .rwd-table td:first-child {
    padding-left: 0;
  }
  .rwd-table th:last-child,
  .rwd-table td:last-child {
    padding-right: 0;
  }
  .rwd-table th{
    text-align: center;
    padding: 1em !important;
  }
  .rwd-table td {
    
    padding: 1em !important;
  }
}


/* THE END OF THE IMPORTANT STUFF */

/* Basic Styling */
body {
background: #4B79A1;
background: -webkit-linear-gradient(to left, #4B79A1 , #283E51);
background: linear-gradient(to left, #4B79A1 , #283E51);        
}
h1 {
  text-align: center;
  font-size: 2.4em;
  color: #f2f2f2;
}
.container {
  display: block;
  text-align: center;
}
h3 {
  display: inline-block;
  position: relative;
  text-align: center;
  font-size: 1.5em;
  color: #cecece;
}
h3:before {
  content: "\25C0";
  position: absolute;
  left: -50px;
  -webkit-animation: leftRight 2s linear infinite;
  animation: leftRight 2s linear infinite;
}
h3:after {
  content: "\25b6";
  position: absolute;
  right: -50px;
  -webkit-animation: leftRight 2s linear infinite reverse;
  animation: leftRight 2s linear infinite reverse;
}
@-webkit-keyframes leftRight {
  0%    { -webkit-transform: translateX(0)}
  25%   { -webkit-transform: translateX(-10px)}
  75%   { -webkit-transform: translateX(10px)}
  100%  { -webkit-transform: translateX(0)}
}
@keyframes leftRight {
  0%    { transform: translateX(0)}
  25%   { transform: translateX(-10px)}
  75%   { transform: translateX(10px)}
  100%  { transform: translateX(0)}
}
</style>
