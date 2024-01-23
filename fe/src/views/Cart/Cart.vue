<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">Shopping cart</h3>
      </div>
    </div>
    <!--    loop over all the cart items and display one by one-->
    <div v-if="cartItems">
      <div
        v-for="cartItem in cartItems"
        :key="cartItem.cartId"
        class="row mt-2 pt-3 justify-content-around"
      >
        <div class="col-2"></div>
        <!-- display image -->
        <div
          class="col-md-3 embed-responsive embed-responsive-16by9"
          v-if="cartItem.book"
        >
          <router-link
            :to="{ name: 'ShowDetails', params: { id: cartItem.book.bookId } }"
          >
            <img
              v-bind:src="cartItem.book.image"
              class="w-100 card-img-top embed-responsive-item"
            />
          </router-link>
        </div>
        <!-- display product name, quantity and price -->
        <div class="col-md-5 px-3" v-if="cartItem.book">
          <div class="card-block px-3">
            <h6 class="card-title">
              <router-link
                :to="{
                  name: 'ShowDetails',
                  params: { id: cartItem.book.bookId },
                }"
                >{{ cartItem.book.bookName }}
              </router-link>
            </h6>
            <p id="item-price" class="mb-0 font-weight-bold">
              {{
                cartItem.book.price.toLocaleString("vi-VN", {
                  style: "currency",
                  currency: "VND",
                })
              }}
            </p>
            <p id="item-quantity" class="mb-0">
              Quantity :
              <input
                size="1"
                class="p-0 h-25 border-bottom border-top-0 border-left-0 border-right-0"
                v-model="cartItem.quantity"
              />
            </p>
            <p id="item-total-price" class="mb-0">
              Total Price:
              <span class="font-weight-bold">
                {{
                  (cartItem.book.price * cartItem.quantity).toLocaleString(
                    "vi-VN",
                    {
                      style: "currency",
                      currency: "VND",
                    }
                  )
                }}</span
              >
            </p>
            <br />
            <a
              href="#"
              class="text-right"
              @click="deleteItem(cartItem.book.bookId)"
              >Remove From Cart</a
            >
          </div>
        </div>
        <div class="col-2"></div>
        <div class="col-12"><hr /></div>
      </div>
    </div>

    <!-- display total price -->
    <div class="total-cost pt-2 text-right">
      <h5 v-if="totalCost !== 0">
        Total :
        {{
          totalCost.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND",
          })
        }}
      </h5>
      <button
        v-if="totalCost !== 0"
        type="button"
        class="btn btn-primary confirm"
        @click="addToOrder"
      >
        Confirm Order
      </button>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
const axios = require("axios");
export default {
  data() {
    return {
      token: null,
      totalcost: 0,
    };
  },
  name: "Cart",
  methods: {
    listCartItems() {
      axios
        .get(`${this.$store.state.baseURL}api/v1/viewCart`, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((res) => {
          console.log("result cart : ", res.data);
          this.$store.commit("setCartItems", res.data);
          // this.cartItems = res.data;
        })
        .catch((err) => {
          console.log("get cart error: ", err);
        });
    },
    addToOrder() {
      const newOrder = {
        listCart: this.cartItems,
        totalCost: this.totalCost,
      };
      console.log(newOrder);
      axios
        .post(`${this.$store.state.baseURL}api/v1/addToOrder`, newOrder, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then(() => {
          // this.cartItems = null;
          this.$store.commit("setCartItems", "null");
          Swal.fire({
            text: "You ordered successfully !",
            icon: "success",
            allowOutsideClick: true,
          });
          this.$router.push({ name: "Order" });
        })
        .catch(() => {
          console.log("error when addToOrder");
        });
    },
    deleteItem(bookId) {
      axios
        .delete(`${this.$store.state.baseURL}api/v1/deleteCart/${bookId}`, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then(() => {
          this.$store.commit("delItemCart", bookId);
          console.log("delete item success");
        })
        .catch(() => {
          console.log("error when delete item");
        });
    },
  },
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.replace({ name: "Signin" });
    }
    this.token = localStorage.getItem("token");
    this.listCartItems();
  },
  computed: {
    totalCost() {
      if (this.cartItems && Array.isArray(this.cartItems)) {
        return this.cartItems.reduce(
          (total, cartItem) => total + cartItem.book.price * cartItem.quantity,
          0
        );
      }
      return 0;
    },
    cartItems() {
      return this.$store.state.cartItems;
    },
  },
};
</script>

<style scoped>
h4,
h5 {
  font-family: "Roboto", sans-serif;
  color: #484848;
  font-weight: 700;
}

.embed-responsive .card-img-top {
  object-fit: cover;
}

#item-price {
  color: #232f3e;
}

#item-quantity {
  float: left;
}

#item-total-price {
  float: right;
}

.confirm {
  font-weight: bold;
  font-size: larger;
}
</style>
