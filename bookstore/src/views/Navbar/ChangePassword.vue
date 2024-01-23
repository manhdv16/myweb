<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Change password</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>Old password</label>
            <input type="password" class="form-control" v-model="oldPass" />
            <small v-if="oldPass === ''" class="text-danger"
              >Please enter old password</small
            >
          </div>
          <div class="form-group">
            <label>New password</label>
            <input type="password" class="form-control" v-model="newPass" />
            <small v-if="newPass === ''" class="text-danger"
              >Please enter new password</small
            >
          </div>
          <div class="form-group">
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

          <button type="button" class="btn btn-primary" @click="handleBack">
            Back
          </button>
          <button
            type="button"
            class="btn btn-primary"
            @click="handleChangePassword"
            :disabled="passwordDoNotMatch"
          >
            Save change password
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
      oldPass: null,
      newPass: null,
      reNewPass: null,
      passwordDoNotMatch: false,
    };
  },
  methods: {
    handleChangePassword() {
      if (this.oldPass !== null && this.newPass !== null) {
        const form = new FormData();
        form.append("oldPass", this.oldPass);
        form.append("newPass", this.newPass);
        axios
          .post(`${this.$store.state.baseURL}api/v1/changepassword`, form, {
            headers: {
              Authorization: `Bearer ${this.$store.state.token}`,
            },
          })
          .then(() => {
            Swal.fire({
              text: "Change password successfully!",
              icon: "success",
              allowOutsideClick: true,
            });
            this.$router.go(-1);
          })
          .catch(() => {
            Swal.fire({
              text: "Incorrect old password",
              icon: "error",
              allowOutsideClick: true,
            });
          });
      } else {
        window.alert("Please fill out all fields");
      }
    },
    checkReNewPassword() {
      this.passwordDoNotMatch = this.newPass !== this.reNewPass;
    },
    handleBack() {
      this.$router.go(-1);
    },
  },
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
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
