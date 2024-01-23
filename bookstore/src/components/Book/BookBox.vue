<template>
  <div class="card h-100 w-100">
    <div>
      <a
        class="book-close"
        @click="handleCloseClick(book.bookId)"
        v-show="$route.name == 'AdminBook'"
      >
        <i class="fa fa-times-circle"></i>
      </a>
    </div>
    <div class="embed-responsive embed-responsive-16by9">
      <img
        class="card-img-top embed-responsive-item"
        :src="book.image"
        alt="Book Image"
      />
    </div>

    <div class="card-body">
      <router-link :to="{ name: 'ShowDetails', params: { id: book.bookId } }"
        ><h5 class="card-title font-weight-bold">
          {{ book.bookName }}
        </h5></router-link
      >
      <p class="card-text font-weight-bold">
        {{
          book.price.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND",
          })
        }}
      </p>
      <p class="card-text font-weight-bold" v-if="book.sold < book.quantity">
        Sold: {{ book.sold }}
      </p>
      <p class="card-text font-weight-bold" v-if="book.sold > book.quantity">
        Sold out
      </p>
      <p class="card-text font-italic">
        {{ book.description.substring(0, 40) }}...
      </p>
      <router-link
        id="edit-book"
        :to="{ name: 'EditBook', params: { id: book.bookId } }"
        v-show="$route.name == 'AdminBook'"
      >
        Edit
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "BookBox",
  props: ["book"],
  methods: {
    showDetails() {
      this.$router.push({
        name: "ShowDetails",
        params: { id: this.book.bookId },
      });
    },
    handleCloseClick(bookId) {
      const confirm = window.confirm(
        "Are you sure you want delete this book ?"
      );
      if (confirm) {
        axios
          .delete(`${this.$store.state.baseURL}api/v1/deleteBook/${bookId}`, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          })
          .then(() => {
            this.$store.dispatch("fetchData");
          })
          .catch((err) => {
            console.log("err when delete book", err);
          });
      }
    },
  },
};
</script>

<style scoped>
.embed-responsive .card-img-top {
  object-fit: cover;
  z-index: 0; /* Thêm dòng này để đảm bảo hình ảnh nằm dưới nút */
}
a {
  text-decoration: none;
}
.book-close {
  color: #ffffff;
  position: absolute;
  line-height: 24px;
  font-size: 24px;
  right: 10px;
  top: 10px;
  cursor: pointer; /* Thêm dòng này để biểu thị rằng nút có thể được nhấp chuột */
  transition: color 0.3s; /* Thêm hiệu ứng chuyển đổi màu khi rê chuột vào nút */
  z-index: 1;
}
.book-close:hover {
  color: #f80303; /* Màu sẽ thay đổi khi rê chuột vào nút */
}
.card-title {
  color: #484848;
  font-size: 1.1rem;
  font-weight: 400;
}

.card-title:hover {
  font-weight: bold;
}

.card-text {
  font-size: 0.9rem;
}

#edit-book {
  float: right;
}
</style>
