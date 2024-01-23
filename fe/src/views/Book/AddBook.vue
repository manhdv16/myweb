<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Add new Book</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>Category</label>
            <select class="form-control" v-model="categoryId" required>
              <option
                v-for="category of categories"
                :key="category.categoryId"
                :value="category.categoryId"
              >
                {{ category.categoryName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>Book Name</label>
            <input
              type="text"
              class="form-control"
              v-model="bookName"
              required
            />
          </div>
          <div class="form-group">
            <label>Author</label>
            <input type="text" class="form-control" v-model="author" required />
          </div>
          <div class="form-group">
            <label>Description</label>
            <input
              type="text"
              class="form-control"
              v-model="description"
              required
            />
          </div>
          <div class="form-group">
            <label>ImageURL</label>
            <input
              type="file"
              class="form-control-file"
              @change="handleFileChange"
              required
            />
            <img
              v-if="imagePreview"
              :src="imagePreview"
              alt="Selected Image"
              style="max-width: 100%; margin-top: 10px"
            />
          </div>
          <div class="form-group">
            <label>Price</label>
            <input
              type="number"
              class="form-control"
              v-model="price"
              required
            />
          </div>
          <div class="form-group">
            <label>Quantity</label>
            <input
              type="number"
              class="form-control"
              v-model="quantity"
              required
            />
          </div>
          <div>
            <button type="button" class="btn btn-secondary" @click="cancelAdd">
              Cancel
            </button>
            <button type="button" class="btn btn-primary" @click="addBook">
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
import { mapState } from "vuex";
export default {
  data() {
    return {
      categoryId: null,
      bookName: null,
      author: null,
      description: null,
      image: null,
      imagePreview: null,
      price: null,
      quantity: null,
      token: null,
    };
  },
  methods: {
    handleFileChange(event) {
      const file = event.target.files[0];
      this.imagePreview = URL.createObjectURL(file);
      console.log("Selected file:", file.name);
      this.image = file;
    },
    addBook() {
      const formData = new FormData();
      formData.append("categoryId", this.categoryId);
      formData.append("bookName", this.bookName);
      formData.append("author", this.author);
      formData.append("description", this.description);
      formData.append("image", this.image);
      formData.append("price", this.price);
      formData.append("quantity", this.quantity);

      const config = {
        headers: {
          Authorization: `Bearer ${this.token}`,
          "Content-Type": "multipart/form-data",
        },
      };
      axios
        .post(`${this.$store.state.baseURL}api/v1/addBook`, formData, config)
        .then(() => {
          this.$store.dispatch("fetchData");
          this.$router.push({ name: "AdminBook" });
          Swal.fire({
            text: "Book Added Successfully!",
            icon: "success",
            allowOutsideClick: true,
          });
        })
        .catch(() => console.log("error when addbook"));
    },
    cancelAdd() {
      this.$router.go(-1);
    },
  },
  computed: {
    ...mapState(["categories"]),
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
