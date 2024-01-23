<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Add new Category</h4>
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
              type="text"
              class="form-control"
              v-model="description"
              rows="5"
              required
            >
            </textarea>
          </div>

          <div>
            <button
              type="button"
              class="btn btn-secondary"
              @click="handleCancel"
            >
              Cancel
            </button>
            <span style="margin-right: 10px"></span>
            <button type="button" class="btn btn-primary" @click="addCategory">
              Save
            </button>
          </div>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      categoryName: null,
      description: null,
      token: null,
    };
  },
  methods: {
    async addCategory() {
      const newCategory = {
        categoryName: this.categoryName,
        description: this.description,
      };
      await axios
        .post(`${this.$store.state.baseURL}api/v1/addCategory`, newCategory, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then(() => {
          this.$store.dispatch("fetchData");
          this.$router.push({ name: "AdminCategory" });
          Swal.fire({
            text: "Category Added Successfully!",
            icon: "success",
            allowOutsideClick: false,
          });
        })
        .catch((err) => console.log(err));
    },
    handleCancel() {
      this.$router.go(-1);
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
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
    this.token = localStorage.getItem("token");
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
