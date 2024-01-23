<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Your Orders</h4>
      </div>
    </div>

    <div
      v-for="order in orderList"
      :key="order.id"
      class="row mt-2 pt-3 justify-content-around"
    >
      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <!--    display image in left-->
        <img
          v-bind:src="order.image"
          class="w-100 card-img-top embed-responsive-item"
        />
      </div>
      <div class="col-md-9 d-flex justify-content-between px-3">
        <div class="card-block px-3">
          <h6 class="card-title">
            <router-link
              :to="{
                name: 'OrderDetails',
                params: { id: order.id },
                query: { totalCostOrder: order.totalCost },
              }"
              >View Order Detail</router-link
            >
          </h6>
          <p class="mb-0">Total Book: {{ order.totalBook }}</p>
          <p id="item-price" class="mb-0 font-weight-bold">
            Total Cost :
            {{
              order.totalCost.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
              })
            }}
          </p>
          <p id="item-total-price">Ordered on : {{ order.orderDate }}</p>
        </div>
        <div class="card-block px-3 text-center">
          <h6 class="mb-0 font-weight-bold">Status</h6>
          <br />
          <span v-if="order.status === 'PROCESSING'" style="color: orange"
            >Processing</span
          >
          <span v-else-if="order.status === 'DELIVERED'" style="color: blue"
            >Delivered</span
          >
        </div>
        <br />
        <button
          v-if="checKStatus(order.status) && order.reviewed === false"
          class="btn btn-success mt-2"
          @click="redirectToReivews(order.id)"
        >
          Review
        </button>
      </div>
      <div class="col-12"><hr /></div>
    </div>
  </div>
</template>

<script>
const axios = require("axios");
export default {
  data() {
    return {
      token: null,
      orderList: [],
    };
  },
  name: "Order",
  methods: {
    // list of order histories
    listOrders() {
      axios
        .get(`${this.$store.state.baseURL}api/v1/orders`, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((response) => {
          if (response.status == 200) {
            // for each order populate orderList
            this.orderList = response.data.map((order) => ({
              id: order.orderId,
              totalCost: order.totalAmount,
              orderDate: order.orderDate.substring(0, 10),
              image: order.image,
              totalBook: order.totalBook,
              status: order.status,
              reviewed: order.reviewed,
            }));
            this.orderList = this.orderList.sort((a, b) => {
              return new Date(b.orderDate) - new Date(a.orderDate);
            });
          }
        })
        .catch((err) => {
          console.log("error when get listOrder", err);
        });
    },
    redirectToReivews(orderId) {
      console.log(orderId);
      this.$router.push({
        name: "Review",
        params: { id: orderId },
      });
    },
    checKStatus(status) {
      if (status === "PROCESSING") return false;
      if (status === "DELIVERED") return true;
    },
  },
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
    this.token = localStorage.getItem("token");
    this.listOrders();
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
</style>
