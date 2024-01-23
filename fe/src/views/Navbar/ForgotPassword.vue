<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Forgot Password</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group" v-if="!isSubmit">
            <label>User Name</label>
            <input type="text" class="form-control" v-model="username" />
            <small v-if="!isFilled && username === ''" class="text-danger"
              >Please enter your user name</small
            >
          </div>
          <div class="form-group" v-if="!isSubmit">
            <label>Email</label>
            <input type="text" class="form-control" v-model="email" />
            <small v-if="!isFilled && email === ''" class="text-danger"
              >Please enter your email</small
            >
          </div>
          <div class="form-group" v-if="isSubmit">
            <label>OTP code</label>
            <input type="text" class="form-control" v-model="otp" />
            <small v-if="!isFilled1 && otp === ''" class="text-danger"
              >Please enter OTP from your email</small
            >
          </div>
          <div class="form-group">
            <button type="button" class="btn btn-primary" @click="handleBack">
              Back
            </button>
            <button
              v-if="!isSubmit"
              type="button"
              class="btn btn-primary"
              @click="handleSubmit"
            >
              Submit
            </button>
            <button
              v-if="isSubmit"
              type="button"
              class="btn btn-primary"
              @click="redirectToSetPass"
            >
              Send
            </button>
          </div>
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
      isSubmit: false,
      isFilled: true,
      isFilled1: true,
      username: "",
      email: "",
      otp: "",
    };
  },
  methods: {
    handleBack() {
      this.$router.go(-1);
    },
    handleSubmit() {
      if (this.username === "" || this.email === "") {
        this.isFilled = false;
      } else {
        const form = new FormData();
        form.append("username", this.username);
        form.append("email", this.email);
        axios
          .post(`${this.$store.state.baseURL}api/v1/forgot-password`, form)
          .then((res) => {
            alert(res.data.message);
            this.isSubmit = true;
          })
          .catch((err) => {
            alert(err.response.data.message);
          });
      }
    },
    redirectToSetPass() {
      if (this.otp === "") {
        this.isFilled1 = false;
      } else {
        axios
          .get(
            `${this.$store.state.baseURL}api/v1/verify?otp=${this.otp}?username=${this.username}`
          )
          .then((res) => {
            window.alert(res.data.message);
            this.$router.push({
              name: "CreateNewPass",
              query: { username: this.username },
            });
          })
          .catch((err) => {
            alert(err.response.data.message);
          });
      }
    },
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
.form-group {
  margin-left: 20px;
}
</style>
