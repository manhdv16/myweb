<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Create Password</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group" v-if="!isSubmit">
            <label>New password</label>
            <input type="password" class="form-control" v-model="newPass" />
            <small v-if="!isFilled && newPass === ''" class="text-danger"
              >Please enter new password</small
            >
          </div>
          <div class="form-group" v-if="!isSubmit">
            <label>Re-enter password</label>
            <input
              type="password"
              class="form-control"
              v-model="reNewPass"
              @input="checkReNewPassword"
            />
            <small v-if="passwordDoNotMatch" class="text-danger"
              >Password Do Not Match</small
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
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      username: "",
      newPass: "",
      reNewPass: "",
      isSubmit: false,
      passwordDoNotMatch: false,
      isFilled: true,
    };
  },
  methods: {
    handleBack() {
      this.$router.push({ name: "Signin" });
    },
    handleSubmit() {
      if (this.newPass === "") {
        window.alert("Please enter new password");
        this.isFilled = false;
      } else if (this.newPass !== this.reNewPass) {
        window.alert("Password Do Not Match");
      } else {
        const form = new FormData();
        form.append("newPass", this.newPass);
        form.append("username", this.username);
        axios
          .put(`${this.$store.state.baseURL}api/v1/setpassword`, form)
          .then((res) => {
            Swal.fire({
              text: res.data.message,
              icon: "success",
              allowOutsideClick: true,
            });
            this.$router.push({ name: "Signin" });
          })
          .catch(() => {
            console.log("err");
          });
      }
    },
    checkReNewPassword() {
      this.passwordDoNotMatch = this.newPass !== this.reNewPass;
    },
  },
  beforeRouteEnter(to, from, next) {
    if (from.name === "ForgotPassword") {
      next();
    } else {
      next({ name: "Signin" });
    }
  },
  mounted() {
    this.username = this.$route.query.username;
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
