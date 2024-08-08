<template>
  <div>
    <input v-model="newItemName" @keyup.enter="addItem" placeholder="Type a new item name and press Enter" />
    <input v-model="newItemAddress" @keyup.enter="addItem" placeholder="Type an address and press Enter" />
    <button @click="addItem">Add Item</button>
    <ul>
      <li v-for="item in items" :key="item.idx">
        {{ item.idx }}: {{ item.name }} ({{ item.address }})
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newItemName: '', // 입력 필드의 값 (name)
      newItemAddress: '' // 입력 필드의 값 (address)
    };
  },
  computed: {
    items() {
      return this.$store.getters.items; // Vuex 스토어에서 items를 가져옵니다
    }
  },
  methods: {
    addItem() {
      if (this.newItemName.trim() !== '' && this.newItemAddress.trim() !== '') {
        const newItem = {
          name: this.newItemName,
          address: this.newItemAddress
        };

        this.$store.dispatch('addItem', newItem); // Vuex 액션을 호출하여 아이템을 추가합니다

        // 입력 필드 비우기
        this.newItemName = '';
        this.newItemAddress = '';
      }
    }
  }
};
</script>

<style scoped>
/* 스타일을 여기에 추가할 수 있습니다 */
input {
  padding: 8px;
  font-size: 16px;
  margin-right: 10px;
}

button {
  padding: 8px;
  font-size: 16px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 5px 0;
}
</style>
