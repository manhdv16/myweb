<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Our Books</h4>
        <router-link id="back" :to="{ name: 'Home' }">
          <button class="btn" @click="goBack">Back</button>
        </router-link>
      </div>
    </div>
    <div class="row" v-if="books">
      <div
        v-for="book of paginatedBooks"
        :key="book.bookId"
        class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
      >
        <BookBox :book="book"> </BookBox>
      </div>
    </div>
    <div class="row mt-3" v-if="books">
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
import BookBox from "../../components/Book/BookBox";
import { mapGetters } from "vuex";
export default {
  name: "Book",
  components: { BookBox },
  data() {
    return {
      currentPage: 1,
      pageSize: 6,
      books: null,
      searchQuery: this.$route.query.searchQuery || "",
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
    handleSearch() {
      this.books = this.searhBooks(this.searchQuery);
      console.log("books search:", this.books);
      this.totalPages = Math.ceil(this.books.length / this.pageSize);
    },
  },
  computed: {
    ...mapGetters(["searhBooks"]),
    paginatedBooks() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.books.slice(startIndex, endIndex);
    },
  },
  watch: {
    "$route.query.searchQuery"(newSearchQuery, oldSearchQuery) {
      if (newSearchQuery !== oldSearchQuery) {
        this.searchQuery = newSearchQuery || "";
        // Trigger a new search based on the updated query
        this.handleSearch();
      }
    },
  },
  mounted() {
    this.handleSearch();
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
