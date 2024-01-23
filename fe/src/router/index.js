import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Navbar/Home.vue";
import Login from "../views/Navbar/login.vue";
import Register from "../views/Navbar/register.vue";
import PageNotFound from "../views/Navbar/PageNotFound.vue";
import Profile from "../views/Navbar/Profile.vue";
import ChangePassword from "../views/Navbar/ChangePassword.vue";
import ForgotPassword from "../views/Navbar/ForgotPassword.vue";
import CreateNewPass from "../views/Navbar/CreateNewPass.vue";

import Admin from "../views/Admin/Admin.vue";
import Book from "../views/Book/Book.vue";
import AddBook from "../views/Book/AddBook.vue";
import BookDetail from "../views/Book/BookDetail.vue";
import EditBook from "../views/Book/EditBook.vue";
import BookSearch from "../views/Book/BookSearch.vue";
import Category from "../views/Category/Category.vue";
import AddCategory from "../views/Category/AddCategory.vue";
import EditCategory from "../views/Category/EditCategory.vue";

import Cart from "../views/Cart/Cart.vue";
import Order from "../views/Order/Order.vue";
import OrderDetail from "../views/Order/OrderDetail.vue";
import AdminOrder from "../views/Order/AdminOrder.vue";
import Review from "../views/Order/Review.vue";
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Signin",
    component: Login,
  },
  {
    path: "/register",
    name: "Signup",
    component: Register,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/changepassword",
    name: "ChangePassword",
    component: ChangePassword,
  },
  {
    path: "/forgotpassword",
    name: "ForgotPassword",
    component: ForgotPassword,
  },
  {
    path: "/createnewpass",
    name: "CreateNewPass",
    component: CreateNewPass,
  },

  //Admin routes
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
  },
  //Book routes
  {
    path: "/book",
    name: "Book",
    component: Book,
  },
  {
    path: "/bookSearch",
    name: "BookSearch",
    component: BookSearch,
  },
  {
    path: "/admin/book",
    name: "AdminBook",
    component: Book,
  },
  {
    path: "/admin/book/add",
    name: "AddBook",
    component: AddBook,
  },
  {
    path: "/admin/book/:id",
    name: "EditBook",
    component: EditBook,
  },
  {
    path: "/book/show/:id",
    name: "ShowDetails",
    component: BookDetail,
  },
  //Category routes
  {
    path: "/category",
    name: "Category",
    component: Category,
  },
  {
    path: "/admin/category",
    name: "AdminCategory",
    component: Category,
  },
  {
    path: "/admin/category/add",
    name: "AddCategory",
    component: AddCategory,
  },
  {
    path: "/admin/category/:id",
    name: "EditCategory",
    component: EditCategory,
  },
  {
    path: "/admin/order",
    name: "AdminOrder",
    component: AdminOrder,
  },
  {
    path: "/:catchAll(.*)",
    name: "PageNotFound",
    component: PageNotFound,
  },
  {
    path: "/cart",
    name: "Cart",
    component: Cart,
  },
  {
    path: "/order",
    name: "Order",
    component: Order,
  },
  {
    path: "/order/:id",
    name: "OrderDetails",
    component: OrderDetail,
  },
  {
    path: "/review/:id",
    name: "Review",
    component: Review,
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
