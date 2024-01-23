<template>
  <div class="container mt-5">
    <h2>Your reviews</h2>
    <br />
    <p>Book quality:</p>
    <div class="rating">
      <span
        v-for="star in stars"
        :key="star"
        @click="selectRating(star)"
        @mouseover="hoverRating(star)"
        @mouseleave="resetHover"
      >
        <i
          class="fa star-icon"
          :class="{
            'fa-star': ratedStars >= star || hoverStars >= star,
            'fa-star-o': ratedStars < star && hoverStars < star,
          }"
        ></i>
      </span>
    </div>

    <div class="form-group mt-4">
      <label for="comment">Comment:</label>
      <textarea
        class="form-control"
        id="comment"
        v-model="comment"
        required
      ></textarea>
    </div>
    <div>
      <button type="button" class="btn btn-secondary" @click="handleCancel">
        Cancel
      </button>
      <span style="margin-right: 10px"></span>
      <button type="button" class="btn btn-primary" @click="submitReview">
        Send
      </button>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import axios from "axios";
export default {
  data() {
    return {
      stars: [1, 2, 3, 4, 5],
      ratedStars: 5,
      hoverStars: 0,
      comment: "",
    };
  },
  methods: {
    selectRating(star) {
      this.ratedStars = star;
    },
    hoverRating(star) {
      this.hoverStars = star;
    },
    resetHover() {
      this.hoverStars = 0;
    },
    submitReview() {
      const form = {
        rating: this.ratedStars,
        content: this.comment,
        orderId: this.orderId,
      };
      console.log(form);
      axios
        .post(`${this.$store.state.baseURL}api/v1/addComment`, form, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        .then((res) => {
          console.log("added comment", res);
          Swal.fire({
            text: "Added comment success!",
            icon: "success",
            allowOutsideClick: false,
          });
          this.$router.go(-1);
        })
        .catch((err) => {
          console.log("err when add cmt", err);
        });
      this.ratedStars = 0;
      this.comment = "";
    },
    handleCancel() {
      this.$router.go(-1);
    },
  },
  beforeRouteEnter(to, from, next) {
    if (from.name === "Order") {
      next();
    } else {
      next({ name: "Signin" });
    }
  },
  mounted() {
    this.orderId = this.$route.params.id;
    this.token = localStorage.getItem("token");
  },
};
</script>

<style scoped>
.rating {
  font-size: 24px;
  cursor: pointer;
}

.star-icon {
  color: #ffcc00;
  margin-right: 5px;
  transition: transform 0.2s ease-in-out;
}

.star-icon:hover {
  transform: scale(1.2);
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
