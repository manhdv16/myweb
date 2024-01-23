<template>
  <div class="container">
    <div
      v-for="(orderItem, index) in orderItems"
      :key="index"
      class="row mt-2 pt-3 justify-content-around"
    >
      <div class="col-1"></div>
      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <img
          :src="orderItem.book.image"
          class="w-100 card-img-top embed-responsive-item"
        />
        <hr />
      </div>

      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <h6 class="card-title">
            <router-link
              :to="{
                name: 'ShowDetails',
                params: { id: orderItem.book.bookId },
              }"
            >
              {{ orderItem.book.bookName }}
            </router-link>
          </h6>
          <p id="item-price" class="mb-0 font-weight-bold">
            Price:
            {{
              orderItem.book.price.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
              })
            }}
          </p>
          <p id="item-quantity" class="mb-0 font-weight-bold">
            Quantity : {{ orderItem.quantity }}
          </p>
          <p id="item-total-price" class="mb-0 font-weight-bold">
            Total Price :
            <span class="font-weight-bold">{{
              (orderItem.book.price * orderItem.quantity).toLocaleString(
                "vi-VN",
                {
                  style: "currency",
                  currency: "VND",
                }
              )
            }}</span>
          </p>
        </div>
      </div>
      <div class="col-1"></div>
    </div>

    <div class="total-cost pt-2 text-right" v-if="totalCostOrder !== 0">
      <h5>
        Total Cost :
        {{
          totalCostOrder.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND",
          })
        }}
      </h5>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "OrderItems",

  data() {
    return {
      orderItems: [],
      token: null,
      orderId: 0,
      totalCostOrder: 0,
    };
  },

  methods: {
    getListOrderDetail() {
      axios
        .get(`${this.$store.state.baseURL}api/v1/order/${this.orderId}`, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then(
          (response) => {
            if (response.status === 200) {
              this.orderItems = response.data;
              console.log(this.orderItems);
            }
          },
          (err) => {
            console.log(err);
          }
        );
    },
  },

  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
    this.orderId = this.$route.params.id;
    this.token = localStorage.getItem("token");
    this.getListOrderDetail();
    if (this.$route.query.redirectReview !== undefined) {
      this.redirectReview = this.$route.query.redirectReview;
    }
    if (this.$route.query.totalCostOrder !== undefined) {
      this.totalCostOrder = this.$route.query.totalCostOrder;
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

.embed-responsive .card-img-top {
  object-fit: cover;
}
</style>
