<script>
export default {
    data() {
        return {
            category: [
            { text: "놀거리" },
            { text: "맛집" },
            { text: "카페" }
            ],
            val_sel: "맛집"
        }
    }
}
</script>

<template>
<!-- 카테고리 선택 -->
<div>
    <select v-model="val_sel">  <!-- val_sel에 데이터 연결 -->
        <option v-for="(item, idx) in category" v-bind:key="idx"> <!-- 배열의 각 항목을 반복하여 드롭다운 메뉴의 항목을 만든다?? -->
            {{ item.text }} <!-- 현재 선택된 항목을 화면에 표시 -->
        </option>
    </select>
</div>
</template>

<style scoped>

</style>