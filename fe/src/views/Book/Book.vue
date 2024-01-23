<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Our Books</h4>
        <router-link
          id="add-book"
          :to="{ name: 'AddBook' }"
          v-show="$route.name == 'AdminBook'"
        >
          <button class="btn">Add a new Book</button>
        </router-link>
        <router-link id="back" :to="{ name: 'Home' }">
          <button class="btn" @click="goBack">Back</button>
        </router-link>
      </div>
    </div>
    <div class="row">
      <div
        v-for="book of paginatedBooks"
        :key="book.bookId"
        class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
      >
        <BookBox :book="book"> </BookBox>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12 text-center">
        <button @click="prevPage" :disabled="currentPage === 1">
          Previous
        </button>
        <span class="mx-2">{{ currentPage }} / {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">
          Next
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import BookBox from "../../components/Book/BookBox";
export default {
  name: "Book",
  components: { BookBox },
  data() {
    return {
      currentPage: 1,
      pageSize: 9,
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage -= 1;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage += 1;
      }
    },
  },
  computed: {
    ...mapState(["books"]),
    totalPages() {
      console.log(this.books.length);
      return Math.ceil(this.books.length / this.pageSize);
    },
    paginatedBooks() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.books.slice(startIndex, endIndex);
    },
  },
  mounted() {
    if (this.$route.name === "AdminBook") {
      const listRoles =
        this.$store.state.roles || localStorage.getItem("listRoles");
      if (listRoles === null) {
        this.$router.replace({ name: "Signin" });
      } else if (
        !listRoles.includes("ROLE_ADMIN") &&
        !listRoles.includes("ROLE_MANAGER")
      ) {
        this.$router.replace({ name: "Home" });
      }
    }
  },
};
</script>

<style scoped>
h4 {
  font-family: "Roboto", sans-serif;
  color: #484848;
  font-weight: 700;
}
#add-book {
  float: right;
  font-weight: 500;
}
#back {
  float: left;
  font-weight: 500;
}
</style>
