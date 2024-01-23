<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Edit Book</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form v-if="book">
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
            <textarea
              id="description"
              class="form-control"
              v-model="description"
              rows="5"
              required
            ></textarea>
          </div>
          <div class="form-group">
            <label>ImageURL</label>
            <input
              type="file"
              class="form-control-file"
              @change="handleFileChange"
              required
            />
            <a v-if="imagePreview" class="image-close" @click="removeImage">
              <i class="fa fa-times-circle"></i>
            </a>
            <img
              v-if="imagePreview"
              :src="imagePreview"
              alt="Selected Image"
              style="max-width: 100%; margin-top: 10px; max-height: 50%"
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
            <button type="button" class="btn btn-secondary" @click="cancelEdit">
              Cancel
            </button>
            <button type="button" class="btn btn-primary" @click="editBook">
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
      book: null,
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
      this.image = file;
    },
    removeImage() {
      this.imagePreview = null;
      this.image = null;
    },
    async editBook() {
      const formData = new FormData();
      if (this.categoryId !== this.book.category.categoryId)
        formData.append("categoryId", this.categoryId);
      if (this.bookName !== this.book.bookName)
        formData.append("bookName", this.bookName);
      if (this.author !== this.book.author)
        formData.append("author", this.author);
      if (this.description !== this.book.description)
        formData.append("description", this.description);
      if (this.image !== this.book.image) formData.append("image", this.image);
      if (this.price !== this.book.price) formData.append("price", this.price);
      if (this.quantity !== this.book.quantity)
        formData.append("quantity", this.quantity);
      const config = {
        headers: {
          Authorization: `Bearer ${this.token}`,
          "Content-Type": "multipart/form-data",
        },
      };
      axios
        .put(
          `${this.$store.state.baseURL}api/v1/updateBook/${this.id}`,
          formData,
          config
        )
        .then(() => {
          this.$store.dispatch("fetchData");
          this.$router.push({ name: "AdminBook" });
          Swal.fire({
            text: "Book Updated Successfully!",
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
    ...mapState(["categories"]),
    ...mapState(["books"]),
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
    this.id = this.$route.params.id;
    this.book = this.books.find((book) => book.bookId == this.id);

    this.categoryId = this.book.category.categoryId;
    this.bookName = this.book.bookName;
    this.author = this.book.author;
    this.description = this.book.description;
    this.image = this.book.image;
    this.imagePreview = this.book.image;
    this.price = this.book.price;
    this.quantity = this.book.quantity;
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

.image-close {
  color: #ffffff;
  position: absolute;
  line-height: 24px;
  font-size: 24px;
  right: 10px;
  top: 470px;
  cursor: pointer;
  transition: color 0.3s;
  z-index: 1;
}
.image-close:hover {
  color: #f80303;
}
</style>
