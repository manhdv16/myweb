<template>
  <div class="container mt-5">
    <div class="row login-container">
      <div class="col-sm-12">
        <h2>Register</h2>
        <form class="login-form">
          <div class="form-group">
            <label for="username" class="float-left">Username</label>
            <input
              v-model="username"
              type="text"
              class="form-control"
              id="username"
              name="username"
              placeholder="Enter Username"
              required
              autocomplete="current-username"
            />
            <small v-if="!isFilled && username === ''" class="text-danger"
              >Please enter username</small
            >
          </div>
          <div class="form-group">
            <label for="password" class="float-left">Password</label>
            <input
              v-model="password"
              type="password"
              class="form-control"
              name="password"
              placeholder="Enter password"
              required
              autocomplete="current-password"
            />
            <small v-if="!isFilled && password === ''" class="text-danger"
              >Please enter password</small
            >
          </div>
          <div class="form-group">
            <label for="phonenumber" class="float-left">Phone Number:</label>
            <input
              v-model="phoneNumber"
              type="text"
              class="form-control"
              name="phonenumber"
              placeholder="Enter phone number"
              required
            />
            <small v-if="!isFilled && phoneNumber === ''" class="text-danger"
              >Please enter phone number</small
            >
          </div>
          <div class="form-group">
            <label for="email" class="float-left">Email:</label>
            <input
              type="email"
              class="form-control"
              name="email"
              v-model="email"
              placeholder="abc@gmail.com"
              required
            />
            <small v-if="!isFilled && email === ''" class="text-danger"
              >Please enter email</small
            >
          </div>
          <div class="form-group">
            <label for="address" class="float-left">Address:</label>
            <input
              v-model="address"
              type="text"
              class="form-control"
              name="address"
              placeholder="Enter address"
              required
            />
            <small v-if="!isFilled && address === ''" class="text-danger"
              >Please enter address</small
            >
          </div>
          <button type="button" class="btn btn-primary" @click="goBack">
            Cancel
          </button>
          <button type="button" class="btn btn-primary" @click="handleRegister">
            Register
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      username: "",
      password: "",
      email: "",
      address: "",
      phoneNumber: "",
      isFilled: true,
    };
  },
  methods: {
    handleRegister() {
      if (
        this.username === "" ||
        this.email === "" ||
        this.password === "" ||
        this.address === "" ||
        this.phoneNumber === ""
      ) {
        this.isFilled = false;
        return;
      }
      const userData = {
        userName: this.username,
        password: this.password,
        email: this.email,
        address: this.address,
        phoneNumber: this.phoneNumber,
      };

      axios
        .post(`${this.$store.state.baseURL}api/v1/signup`, userData)
        .then((res) => {
          console.log(res.data);
          alert(res.data.message);
          this.$router.push("/login");
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 5rem;
}

.login-container {
  border: 1px solid darkgrey;
  border-radius: 10px;
  width: 50%;
  margin: 0 auto;
  padding: 20px;
}

.login-form {
  margin-top: 20px;
}

.form-control {
  width: 90%;
  padding: 10px;
  margin-bottom: 10px;
  margin-left: 2%;
}
.form-group label {
  display: block;
  margin-bottom: 5px;
  margin-left: 4%;
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
.float-left {
  float: left;
  clear: left;
  font-weight: bold;
}
</style>
