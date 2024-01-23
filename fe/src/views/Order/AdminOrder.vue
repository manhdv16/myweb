<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Your Orders</h4>
      </div>
      <router-link id="back" :to="{ name: 'Home' }">
        <button class="btn" @click="goBack">Back</button>
      </router-link>
    </div>

    <div
      v-for="order in paginatedOrders"
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
          <p class="mb-0 font-weight-bold">
            Total Book:
            <span class="mb-0 font-weight-normal">{{ order.totalBook }}</span>
          </p>
          <p id="item-price" class="mb-0 font-weight-bold">
            Total Cost :
            <span class="mb-0 font-weight-normal">{{
              order.totalCost.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
              })
            }}</span>
          </p>
          <p class="mb-0 font-weight-bold">
            Ordered on:
            <span class="mb-0 font-weight-normal">{{ order.orderDate }}</span>
          </p>
          <p class="mb-0 font-weight-bold">
            UserName :
            <span class="mb-0 font-weight-normal">{{ order.userName }}</span>
          </p>
          <p class="mb-0 font-weight-bold">
            Address:
            <span class="mb-0 font-weight-normal">{{ order.address }}</span>
          </p>
          <p class="mb-0 font-weight-bold">
            PhoneNumber:
            <span class="mb-0 font-weight-normal">{{ order.phoneNumber }}</span>
          </p>
        </div>
        <div class="card-block px-3 text-center">
          <h6 class="mb-0 font-weight-bold">Status</h6>
          <br />
          <select
            v-model="order.status"
            class="form-control"
            style="width: 150px"
          >
            <option value="PROCESSING" style="color: orange">Processing</option>
            <option value="DELIVERED" style="color: blue">Delivered</option>
          </select>
        </div>

        <br />
        <div class="row text-center">
          <div class="col-md-6">
            <button
              class="btn btn-success mt-4"
              @click="setStatus(order.id, order.status)"
            >
              Save
            </button>
          </div>
          <div class="col-md-6">
            <button class="btn btn-danger mt-4" @click="deleteOrder(order.id)">
              Delete
            </button>
          </div>
        </div>
      </div>
      <div class="col-12"><hr /></div>
    </div>
    <div class="row mt-3">
      <div class="col-12 text-center">
        <button @click="prevPage" :disabled="currentPage === 1">
          Previous
        </button>
        <span class="mx-2"> {{ currentPage }}/{{ totalPages }} </span>
        <button @click="nextPage" :disabled="currentPage === totalPages">
          Next
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import { mapState } from "vuex";
const axios = require("axios");
export default {
  data() {
    return {
      token: null,
      orderList: [],
      currentPage: 1,
      pageSize: 3,
    };
  },
  name: "Order",
  computed: {
    ...mapState(["listOrders"]),
    totalPages() {
      return Math.ceil(this.$store.state.listOrders.length / this.pageSize);
    },
    paginatedOrders() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.$store.state.listOrders.slice(startIndex, endIndex);
    },
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    prevPage() {
      if (this.currentPage > 1) return (this.currentPage -= 1);
    },
    nextPage() {
      if (this.currentPage < this.totalPages) this.currentPage += 1;
    },
    // list of order histories
    getListOrders() {
      axios
        .get(`${this.$store.state.baseURL}api/v1/adminOrder`, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((res) => {
          this.orders = res.data;
          // for each order populate orderList
          this.orderList = this.orders.map((order) => ({
            id: order.orderId,
            userName: order.user.userName,
            address: order.user.address,
            phoneNumber: order.user.phoneNumber,
            totalCost: order.totalAmount,
            orderDate: order.orderDate.substring(0, 10),
            image: order.image,
            totalBook: order.totalBook,
            status: order.status,
          }));
          this.orderList = this.orderList.sort((a, b) => {
            return new Date(b.orderDate) - new Date(a.orderDate);
          });
          this.$store.commit("setListOrders", this.orderList);
        })
        .catch(() => {
          console.log("get list error");
        });
    },
    setStatus(orderId, status) {
      axios
        .put(
          `${this.$store.state.baseURL}api/v1/updateOrder/${orderId}?status=${status}`,
          null,
          {
            headers: {
              Authorization: `Bearer ${this.token}`,
            },
          }
        )
        .then(() => {
          Swal.fire({
            text: "Update status successfully",
            icon: "success",
            allowOutsideClick: true,
          });
        })
        .catch(() => {
          console.log("err when set status");
        });
    },
    deleteOrder(orderId) {
      const confirm = window.confirm(
        "Are you sure you want delete this order ?"
      );
      if (confirm) {
        axios
          .delete(`${this.$store.state.baseURL}api/v1/deleteOrder/${orderId}`, {
            headers: {
              Authorization: `Bearer ${this.token}`,
            },
          })
          .then(() => {
            this.getListOrders();
          })
          .catch((err) => {
            console.log("err when delete", err);
          });
      }
    },
  },
  mounted() {
    const roles = this.$store.state.roles;
    console.log(roles);
    this.token = localStorage.getItem("token");
    this.getListOrders();
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
#back {
  float: left;
  font-weight: 500;
}
</style>
