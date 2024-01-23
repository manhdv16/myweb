<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Our Categories</h4>
        <router-link
          id="add-category"
          :to="{ name: 'AddCategory' }"
          v-show="$route.name == 'AdminCategory'"
        >
          <button class="btn">Add a new Category</button>
        </router-link>
        <router-link id="back" :to="{ name: 'Home' }">
          <button class="btn" @click="goBack">Back</button>
        </router-link>
      </div>
    </div>
    <div class="row">
      <div
        v-for="category of categories"
        :key="category.id"
        class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
      >
        <CategoryBox :category="category"> </CategoryBox>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryBox from "../../components/Category/CategoryBox";
export default {
  name: "Category",
  components: { CategoryBox },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
  computed: {
    categories() {
      return this.$store.state.categories;
    },
  },
  mounted() {
    if (this.$route.name == "AdminCategory") {
      const listRoles =
        this.$store.state.roles || localStorage.getItem("listRoles");
      if (listRoles === null) {
        this.$router.replace({ name: "Signin" });
      } else if (
        !listRoles.includes("ROLE_ADMIN") &&
        !listRoles.includes("ROLE_MANAGER")
      ) {
        this.$router.replace({ name: "Home" });
      }
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

#add-category {
  float: right;
  font-weight: 500;
}
#back {
  float: left;
  font-weight: 500;
}
</style>
