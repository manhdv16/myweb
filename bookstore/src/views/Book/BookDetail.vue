<template>
  <div class="container">
    <div class="row pt-5">
      <div class="col-md-1"></div>
      <div class="col-md-4 col-12">
        <img :src="book.image" :alt="book.bookName" class="img-fluid" />
      </div>
      <div class="col-md-6 col-12 pt-3 pt-md-0">
        <h4>{{ book.bookName }}</h4>
        <h6 class="category font-italic">{{ categoryName }}</h6>
        <h6 class="font-weight-bold">
          {{
            book.price
              ? book.price.toLocaleString("vi-VN", {
                  style: "currency",
                  currency: "VND",
                })
              : "N/A"
          }}
        </h6>
        <h6 class="font-weight-bold">Sold: {{ book.sold }}</h6>
        <div class="d-flex flex-row justify-content-between">
          <div class="input-group col-md-3 col-4 p-0">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">Quantity</span>
            </div>
            <input class="form-control" type="number" v-model="quantity" />
          </div>

          <div class="input-group col-md-3 col-4 p-0">
            <button
              type="button"
              id="add-to-cart-button"
              class="btn"
              @click="addToCart(this.id)"
            >
              Add to Cart
              <ion-icon name="cart-outline" v-pre></ion-icon>
            </button>
          </div>
        </div>

        <div class="description pt-3">
          <h5><strong>Description</strong></h5>
          <textarea
            id="description"
            class="form-control"
            v-model="book.description"
            rows="5"
            readonly
          >
          </textarea>
        </div>

        <div class="mt-3">
          <button
            id="show-cart-button"
            type="button"
            class="btn mr-3 p-1 py-0"
            @click="listCartItems()"
          >
            Show Cart

            <ion-icon name="cart-outline" v-pre></ion-icon>
          </button>
        </div>
      </div>
      <div class="col-md-1"></div>
    </div>
    <div class="reviews pt-5">
      <h5><strong>Reviews</strong></h5>
      <div v-if="reviews && reviews.length > 0">
        <div
          v-for="(review, index) in reviews"
          :key="index"
          class="review-container mb-4"
        >
          <div class="user-info mb-2">
            <strong>{{ review.user.userName }}</strong>
          </div>
          <div class="star-rating mb-2">
            <ion-icon
              name="star"
              v-for="i in Math.round(review.rating)"
              :key="i"
              class="star-icon"
            ></ion-icon>
          </div>
          <div class="review-content">
            <p>{{ review.content }}</p>
          </div>
        </div>
      </div>
      <p v-else>No reviews yet. Be the first one to review!</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      book: {},
      categoryName: null,
      id: null,
      token: null,
      isAddedToWishlist: false,
      quantity: 1,
      reviews: null,
    };
  },
  methods: {
    addToCart(bookId) {
      if (!this.token) {
        this.$router.push({ name: "Signin" });
      }
      const newCart = {
        bookId: bookId,
        quantity: this.quantity,
      };
      axios
        .post(`${this.$store.state.baseURL}api/v1/addToCart`, newCart, {
          headers: {
            Authorization: `Bearer ${this.token}`,
            "Content-Type": "application/json",
          },
        })
        .then(() => {
          Swal.fire({
            text: "Book Added to the cart!",
            icon: "success",
            allowOutsideClick: false,
          });
        })
        .catch(() => {
          console.log("err when add to cart");
        });
    },
    listCartItems() {
      if (!this.token) {
        this.$router.push({ name: "Signin" });
      } else {
        axios
          .get(`${this.$store.state.baseURL}api/v1/viewCart`, {
            headers: {
              Authorization: `Bearer ${this.token}`,
            },
          })
          .then(() => {
            this.$router.push("/cart");
          });
      }
    },
    listComments(id) {
      axios
        .get(`${this.$store.state.baseURL}api/v1/searchCommentByBook/${id}`)
        .then((res) => {
          this.reviews = res.data;
        })
        .catch(() => {
          console.log("get list comments error");
        });
    },
  },
  mounted() {
    this.id = this.$route.params.id;
    this.books = this.$store.state.books;
    this.book = this.books.find((book) => book.bookId == this.id);
    this.categoryName = this.book.category.categoryName;
    this.token = localStorage.getItem("token");
    this.listComments(this.id);
  },
};
</script>

<style>
.category {
  font-weight: 400;
}

/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

#add-to-cart-button {
  background-color: #febd69;
}

#wishlist-button {
  background-color: #b9b9b9;
  border-radius: 0;
}

#show-cart-button {
  background-color: #131921;
  color: white;
  border-radius: 0;
}
.review-container {
  border: 1px solid #ddd;
  padding: 15px;
  border-radius: 8px;
}

.user-info {
  font-weight: bold;
}

.star-rating {
  color: #f1c40f; /* Màu vàng, thay đổi màu sắc theo ý muốn */
}

.star-icon {
  font-size: 1.2em;
}

.review-content {
  margin-top: 10px;
}
</style>
