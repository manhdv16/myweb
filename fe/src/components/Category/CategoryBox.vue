<template>
  <div class="card h-100 w-100">
    <div class="card-body">
      <div>
        <a
          class="book-close"
          @click="handleCloseClick(category.categoryId)"
          v-show="$route.name == 'AdminCategory'"
        >
          <i class="fa fa-times-circle"></i>
        </a>
      </div>
      <h5 class="card-title font-weight-bold">{{ category.categoryName }}</h5>
      <p class="card-text font-italic">
        {{ category.description.substring(0, 65) }}...
      </p>
      <router-link
        id="edit-category"
        :to="{ name: 'EditCategory', params: { id: category.categoryId } }"
        v-show="$route.name == 'AdminCategory'"
      >
        Edit
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CategoryBox",
  props: ["category"],
  methods: {
    handleCloseClick(id) {
      const confirm = window.confirm(
        "Are you sure you want delete this category ?"
      );
      if (confirm) {
        axios
          .delete(`${this.$store.state.baseURL}api/v1/deleteCategory/${id}`)
          .then((res) => {
            this.$store.dispatch("fetchData");
            console.log(res.status);
          })
          .catch((err) => console.log("delete error", err));
      }
    },
    mounted() {
      if (!localStorage.getItem("token")) {
        this.$router.push({ name: "Signin" });
      }
      this.token = localStorage.getItem("token");
      console.log(this.token);
    },
  },
};
</script>

<style scoped>
.embed-responsive .card-img-top {
  object-fit: cover;
}

a {
  text-decoration: none;
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

#edit-category {
  float: right;
}
.book-close {
  color: #ffffff;
  position: absolute;
  line-height: 24px;
  font-size: 24px;
  right: 10px;
  top: 10px;
  cursor: pointer;
  transition: color 0.3s;
  z-index: 1;
}
.book-close:hover {
  color: #f80303;
}
</style>
