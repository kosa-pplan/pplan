<template>
  <div class="table-container">
    <table class="styled-table">
      <thead>
        <tr>
          <th>키</th>
          <th>값</th>
          <th>액션</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(value, key) in data" :key="key">
          <td>{{ key }}</td>
          <td>{{ value.title }}</td>
          <td>
            <button @click="handleButtonClick(value)">자세히 보기</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="modal" v-if="isModalOpen">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h2>경로</h2>
        <CourseDetailModalCompo :courseId="this.courseId" :check="this.check" :message="directions" :locationdata="locationdata" @close="showModal = false, modalType=''"/>
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
      courseId: ''
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {
        const response = await axios.get('http://localhost:8080/api/course/userid', {
          params: { email: "test@gmail.com" }
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
    }
  }
};
</script>

<style scoped>
.table-container {
  display: flex;
  justify-content: center; /* 가로 가운데 정렬 */
  align-items: center; /* 세로 가운데 정렬 (필요한 경우) */
  height: 100%; /* 전체 화면 높이 */
}

.styled-table {
  width: 80%; /* 테이블 너비를 80%로 설정 */
  border-collapse: collapse;
}

.styled-table th, .styled-table td {
  border: 1px solid #dddddd; /* 테두리 색상 */
  text-align: left;
  padding: 8px; /* 여백 */
}

.styled-table th {
  background-color: #f2f2f2; /* 헤더 배경색 */
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
</style>
