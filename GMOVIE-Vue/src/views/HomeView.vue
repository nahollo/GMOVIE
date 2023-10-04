<template>
  <div class="home">
    <router-link to="/user">사용자 목록</router-link>
    <br />
    <button @click="getData">데이터 호출</button>
  </div>
</template>

<script>
import axios from "axios";
const uForm = {
  no: 3,
  name: "테스트",
  email: "test@email.com",
  pwd: "1234",
  gender: 0,
};
const params = {
  params: { no: 1 },
};

export default {
  name: "HomeView",
  methods: {
    getData() {
      axios
        .put("http://localhost:8800/save", uForm)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
a {
  color: #000;
  text-decoration: none;
  font-weight: bold;
  font-size: 1.5rem;
}
</style>
