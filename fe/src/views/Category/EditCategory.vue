<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Edit Category</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>Category Name</label>
            <input
              type="text"
              class="form-control"
              v-model="categoryName"
              required
            />
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea
              id="description"
              class="form-control"
              v-model="description"
              rows="5"
              required
            ></textarea>
          </div>
          <div>
            <button type="button" class="btn btn-secondary" @click="cancelEdit">
              Cancel
            </button>
            <button type="button" class="btn btn-primary" @click="editCategory">
              Save
            </button>
          </div>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>
//
<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      categoryName: null,
      description: null,
      category: null,
    };
  },
  methods: {
    editCategory() {
      const dataForm = new FormData();
      if (this.categoryName !== this.category.categoryName)
        dataForm.append("categoryName", this.categoryName);
      if (this.description !== this.category.description)
        dataForm.append("description", this.description);

      axios
        .put(
          `${this.$store.state.baseURL}api/v1/updateCategory/${this.id}`,
          dataForm,
          {
            headers: {
              Authorization: `Bearer ${this.token}`,
            },
          }
        )
        .then((res) => {
          console.log(res.status);
          this.$store.dispatch("fetchData");
          this.$router.push({ name: "AdminCategory" });
          Swal.fire({
            text: "Category update successfully",
            icon: "success",
            allowOutsideClick: false,
          });
        })
        .catch((err) => console.log("err", err));
    },
    cancelEdit() {
      this.$router.go(-1);
    },
  },
  computed: {
    categories() {
      return this.$store.state.categories;
    },
  },
  beforeRouteEnter(to, from, next) {
    let listRoles = [];
    listRoles = localStorage.getItem("listRoles");
    if (listRoles === null) {
      next({ name: "Signin" });
    } else if (
      !listRoles.includes("ROLE_ADMIN") &&
      !listRoles.includes("ROLE_MANAGER")
    ) {
      next({ name: "Home" });
    }
  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.id = this.$route.params.id;
    this.category = this.categories.find(
      (category) => category.categoryId == this.id
    );
    this.categoryName = this.category.categoryName;
    this.description = this.category.description;
  },
};
</script>

<style scoped>
h4 {
  font-family: "Roboto", sans-serif;
  color: #484848;
  font-weight: 700;
}
.btn {
  margin-top: 10px;
  margin-right: 1rem;
}
.btn-primary {
  background-color: rgb(12, 12, 231);
  color: white;
  font-size: 15px;
}
</style>
