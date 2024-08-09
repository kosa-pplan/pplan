<template>
  <div class="page-container">
    <div>
      <h1>{{ title }}</h1>
      <p>{{ contents }}</p>
      <p>Registration Date: {{ regDate }}</p>

      <div v-if="reviewImageDTOList.length">
        <h2>Images:</h2>
        <div class="image-gallery">
          <div v-for="image in reviewImageDTOList" :key="image.imageId" class="image-item">
            <img
                :src="getImageUrl(image.urlPath)"
                :alt="'Image ' + image.imageId"
                class="thumbnail-image"
                @click="openModal(image.urlPath)"
            />
          </div>
        </div>
      </div>

      <!-- Modal for enlarged image -->
      <div v-if="showModal" class="modal" @click="closeModal">
        <img :src="getImageUrl(enlargedImageUrl)" class="modal-image"/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      id: null,
      title: '',
      contents: '',
      reviewImageDTOList: [],
      regDate: '',
      showModal: false,
      enlargedImageUrl: ''
    };
  },
  mounted() {
    this.id = this.$route.params.id;
    this.fetchData();
  },
  methods: {
    fetchData() {
      const url = `http://localhost:8080/review/${this.id}`;
      axios.get(url)
          .then(response => {
            const data = response.data;
            this.title = data.title;
            this.contents = data.contents;
            this.reviewImageDTOList = data.reviewImageDTOList;
            this.regDate = data.regDate;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    getImageUrl(path) {
      return `http://localhost:8080/imgs/${path}`;
    },
    openModal(imageUrl) {
      this.enlargedImageUrl = imageUrl;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.enlargedImageUrl = '';
    }
  }
};
</script>

<style scoped>
.image-gallery {
  display: flex;
  flex-wrap: wrap;
}

.image-item {
  margin: 10px;
  border: 1px solid #ddd;
  padding: 5px;
  border-radius: 5px;
}

.thumbnail-image {
  width: 150px;
  height: 150px;
  object-fit: cover;
  cursor: pointer;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-image {
  max-width: 90%;
  max-height: 90%;
  cursor: pointer;
}
</style>