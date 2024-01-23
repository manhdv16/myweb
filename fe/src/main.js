import axios from "axios";
import App from "./App.vue";
import router from "./router";

window.axios = require("axios");

import { createApp } from "vue";
import Vuex from "vuex";
const store = new Vuex.Store({
  state: {
    token: null,
    roles: null,
    baseURL: "https://bebookstore-production.up.railway.app/",
    // baseURL: "http://localhost:8088/",
    books: [],
    categories: [],
    cartItems: [],
    listOrders: [],
  },
  getters: {
    token: (state) => state.token,
    searhBooks: (state) => (bookName) => {
      const listBooks = state.books.filter(
        (book) =>
          book.bookName.toLowerCase() === bookName.toLowerCase() ||
          book.bookName.toLowerCase().includes(bookName.toLowerCase())
      );
      return listBooks;
    },
  },
  mutations: {
    setBooks(state, listBooks) {
      state.books = listBooks;
    },
    setCategories(state, listCate) {
      state.categories = listCate;
    },
    setToken(state, { token, listRoles }) {
      state.token = token;
      state.roles = listRoles;
      localStorage.setItem("token", token);
      localStorage.setItem("listRoles", listRoles);
    },
    setCartItems(state, listCartItems) {
      state.cartItems = listCartItems;
    },
    setListOrders(state, listOrders) {
      state.listOrders = listOrders;
    },
    delItemCart(state, bookId) {
      const listItems = state.cartItems;
      const cartItems = listItems.filter((item) => item.book.bookId !== bookId);
      state.cartItems = cartItems;
    },
    clearToken(state) {
      state.token = null;
      state.roles = null;
      localStorage.removeItem("token");
      localStorage.removeItem("listRoles");
    },
  },
  actions: {
    fetchData({ commit }) {
      axios
        .get("https://bebookstore-production.up.railway.app/api/v1/categories")
        .then((res) => {
          commit("setCategories", res.data);
        })
        .catch(() => {
          console.log("get category error");
        });
      axios
        .get("https://bebookstore-production.up.railway.app/api/v1/books")
        .then((res) => {
          commit("setBooks", res.data);
        })
        .catch(() => {
          console.log("get books error");
        });
    },
  },
});
const app = createApp(App);
app.use(Vuex);
app.use(store);
app.use(router).mount("#app");
