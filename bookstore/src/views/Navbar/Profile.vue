<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">My profile</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>User Name</label>
            <input
              type="text"
              class="form-control"
              v-model="userName"
              :disabled="true"
            />
          </div>
          <div class="form-group">
            <label>Email</label>
            <input
              type="text"
              class="form-control"
              v-model="email"
              required
              :disabled="!isEdit"
            />
          </div>
          <div class="form-group">
            <label>Phone Number</label>
            <input
              type="text"
              class="form-control"
              v-model="phoneNumber"
              required
              :disabled="!isEdit"
            />
          </div>

          <div class="form-group">
            <label>Address</label>
            <input
              type="text"
              class="form-control"
              v-model="address"
              required
              :disabled="!isEdit"
            />
          </div>
          <button
            v-if="!isEdit"
            type="button"
            class="btn btn-primary"
            @click="handleBack"
          >
            Back
          </button>
          <button
            v-if="!isEdit"
            type="button"
            class="btn btn-primary"
            @click="handleClickEdit"
          >
            Edit
          </button>
          <button
            v-if="isEdit"
            type="button"
            class="btn btn-primary"
            @click="handleCancel"
          >
            Cancel
          </button>
          <button
            v-if="isEdit"
            type="button"
            class="btn btn-primary"
            @click="handleSave"
          >
            Save
          </button>
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
      userName: null,
      email: null,
      phoneNumber: null,
      address: null,
      token: null,
      user: null,
      isEdit: false,
    };
  },
  methods: {
    getInfor() {
      axios
        .get(`${this.$store.state.baseURL}api/v1/viewInfor`, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((res) => {
          console.log("resdata", res.data);
          this.user = res.data;
          this.userName = this.user.userName;
          this.email = this.user.email;
          this.phoneNumber = this.user.phoneNumber;
          this.address = this.user.address;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleBack() {
      this.$router.go(-1);
    },
    handleCancel() {
      this.isEdit = false;
    },
    handleClickEdit() {
      this.isEdit = true;
    },
    handleSave() {
      this.isEdit = false;
      const data = new FormData();
      if (this.email !== this.user.email) data.append("email", this.email);
      if (this.phoneNumber !== this.user.phoneNumber)
        data.append("phoneNumber", this.phoneNumber);
      if (this.address !== this.user.address)
        data.append("address", this.address);

      axios
        .put(`${this.$store.state.baseURL}api/v1/updateInfor`, data, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((res) => {
          console.log(res.status);
          Swal.fire({
            text: "Profile updated successfully!",
            icon: "success",
            allowOutsideClick: false,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleChangePassword() {
      this.$router.push({ name: "ChangePassword" });
    },
  },
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
    this.token = localStorage.getItem("token");
    this.getInfor();
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
