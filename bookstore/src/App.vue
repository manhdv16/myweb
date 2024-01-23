<template>
  <navigations />

  <div style="min-height: 60vh">
    <router-view v-if="books && categories"> </router-view>
  </div>
</template>

<script>
import axios from "axios";
import Nav from "./components/Nav.vue";
// import { mapState } from "vuex";
export default {
  name: "App",
  components: {
    navigations: Nav,
  },
  data() {
    return {
      baseURL: "https://bebookstore-production.up.railway.app/",
      // baseURL: "http://localhost:8088/",
      books: null,
      categories: null,
    };
  },
  methods: {
    fetchData() {
      axios
        .get(`${this.baseURL}api/v1/books`)
        .then((res) => {
          this.books = res.data.sort((a, b) => {
            return b.sold - a.sold;
          });
          this.$store.commit("setBooks", this.books);
        })
        .catch(() => {
          console.log("get book error");
        });

      axios
        .get(`${this.baseURL}api/v1/categories`)
        .then((res) => {
          this.categories = res.data;
          this.$store.commit("setCategories", res.data);
        })
        .catch(() => {
          console.log("get category error");
        });
    },
  },
  mounted() {
    this.fetchData();
  },
};
</script>

<style>
html {
  overflow-y: scroll;
}
</style>
