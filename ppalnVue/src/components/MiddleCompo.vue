<script>
import LeftPlaceChoice from "@/components/Main/LeftPlaceChoice.vue";
import CenterPlaceList from "@/components/Main/CenterPlaceList.vue";

export default {
  components: {
    LeftPlaceChoice,
    CenterPlaceList,
  },
};
</script>

<template>
  <div class="middle">
    <LeftPlaceChoice class="place-choice" />
    <div class="center-container">
      <CenterPlaceList />
    </div>
  </div>
</template>

<style scoped>
.middle {
  display: flex;
  position: relative;
  width: 100%;
  height: 100vh; /* 전체 화면 높이 */
  overflow: hidden; /* 필요한 경우 추가 */
}

.place-choice {
  position: absolute;
  left: 0;
  padding: 10px;
}

.center-container {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 200px; /* 필요한 경우 조정 */
  overflow: hidden; /* 필요한 경우 추가 */
  position: relative;
}

.map-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain; /* 이미지가 컨테이너에 맞게 조정 */
}
</style>
