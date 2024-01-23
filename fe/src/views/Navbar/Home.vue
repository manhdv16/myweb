<template>
  <div id="home">
    <!-- Page Wrapper -->
    <div id="background-div" class="page-holder bg-cover">
      <div class="container py-5">
        <header class="text-left text-white py-5">
          <h3 class="mb-4 rounded">
            <a
              href="#start-shopping"
              class="bg-white px-2 py-2 rounded"
              id="heading"
              >Start Shopping</a
            >
          </h3>
        </header>
      </div>
    </div>

    <div id="start-shopping" class="container">
      <div class="row">
        <div class="col-12 text-left">
          <h2 class="pt-3">Top Categories</h2>
        </div>
      </div>
      <div class="row">
        <div
          v-for="index in this.category_size"
          :key="index"
          class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
        >
          <CategoryBox :category="categories[index - 1]"> </CategoryBox>
        </div>
      </div>
    </div>

    <hr />
    <div class="container">
      <div class="row">
        <div class="col-12 text-left">
          <h2 class="pt-3">Top Books</h2>
        </div>
      </div>
      <div class="row">
        <div
          v-for="index in this.book_size"
          :key="index"
          class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
        >
          <BookBox :book="books[index - 1]"> </BookBox>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BookBox from "@/components/Book/BookBox";
import CategoryBox from "@/components/Category/CategoryBox";
export default {
  name: "Home",
  components: { BookBox, CategoryBox },
  data() {
    return {
      book_size: 0,
      category_size: 0,
    };
  },
  mounted() {
    this.categories = this.$store.state.categories;
    this.category_size = this.categories.length;
    this.category_size = Math.min(3, this.category_size);
    this.books = this.$store.state.books;
    this.book_size = this.books.length;
    this.book_size = Math.min(6, this.book_size);
  },
};
</script>

<style>
.page-holder {
  min-height: 50vh;
}

.bg-cover {
  background-size: cover !important;
}

#background-div {
  background: url(@/assets/home.png);
}

#heading {
  text-decoration: none;
  font-family: "Roboto", sans-serif;
  font-weight: 400;
  opacity: 0.8;
  font-family: "Josefin Sans", sans-serif;
}

#content {
  opacity: 0.8;
}

h2 {
  font-family: "Josefin Sans", sans-serif;
}
</style>
