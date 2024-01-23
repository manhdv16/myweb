<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <!-- Search Bar -->
      <form class="form-inline ml-auto mr-auto" @submit.prevent="handleSearch">
        <div class="input-group">
          <input
            v-model="searchQuery"
            size="100"
            type="text"
            class="form-control"
            placeholder="Search Items"
            aria-label="Username"
            aria-describedby="basic-addon1"
            @keyup.enter="handleSearch"
          />
          <div class="input-group-prepend">
            <span
              class="input-group-text"
              id="search-button-navbar"
              @click="handleSearch"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-search"
                viewBox="0 0 16 16"
              >
                <path
                  d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
                />
              </svg>
            </span>
          </div>
        </div>
      </form>

      <!-- DropDowns -->
      <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
          <a
            class="nav-link text-light dropdown-toggle"
            href="#"
            id="navbarDropdownBrowse"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            Browse
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownBrowse">
            <router-link class="dropdown-item" :to="{ name: 'Home' }"
              >Home</router-link
            >
            <router-link class="dropdown-item" :to="{ name: 'Book' }"
              >Book</router-link
            >
            <router-link class="dropdown-item" :to="{ name: 'Category' }"
              >Category</router-link
            >
          </div>
        </li>

        <li class="nav-item dropdown">
          <a
            class="nav-link text-light dropdown-toggle"
            href="#"
            id="navbarDropdownAccounts"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            Accounts
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownAccounts">
            <router-link
              v-if="token && checkRole"
              class="dropdown-item"
              :to="{ name: 'Admin' }"
              >Admin</router-link
            >
            <router-link
              class="dropdown-item"
              v-if="token"
              :to="{ name: 'Profile' }"
              >Profile</router-link
            >
            <router-link
              class="dropdown-item"
              v-if="!token"
              :to="{ name: 'Signin' }"
              >Log In</router-link
            >
            <router-link
              class="dropdown-item"
              v-if="!token"
              :to="{ name: 'Signup' }"
              >Sign Up</router-link
            >
            <router-link
              class="dropdown-item"
              v-if="token"
              :to="{ name: 'ChangePassword' }"
              >Change Password</router-link
            >
            <router-link
              class="dropdown-item"
              v-if="token"
              :to="{ name: 'Signin' }"
              @click="signout"
              >Sign Out</router-link
            >
          </div>
        </li>

        <li class="nav-item">
          <router-link
            class="nav-link text-light"
            v-if="token"
            :to="{ name: 'Order' }"
            >Orders</router-link
          >
        </li>
        <li class="nav-item">
          <div id="cart">
            <!-- <span id="nav-cart-count">{{ cartCount }}</span> -->
            <router-link
              class="text-light"
              :to="{ name: 'Cart' }"
              @click="checkLogin"
              ><i class="fa fa-shopping-cart" style="font-size: 36px"></i
            ></router-link>
          </div>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      searchQuery: "",
    };
  },
  methods: {
    handleSearch() {
      // this.$store.getters("findBook", this.searchQuery);
      this.$router.push({
        name: "BookSearch",
        query: { searchQuery: this.searchQuery },
      });
    },
    signout() {
      this.$store.commit("clearToken");
      localStorage.removeItem("token");
      this.$router.push({ name: "Home" });
    },
    checkLogin() {
      if (this.token) {
        this.$router.push({ name: "Cart" });
      } else {
        this.$router.push({ name: "Signin" });
      }
    },
  },
  computed: {
    token() {
      return this.$store.state.token || localStorage.getItem("token");
    },
    checkRole() {
      let listRoles = [];
      listRoles = this.$store.state.roles || localStorage.getItem("listRoles");
      console.log("listRoles: ", listRoles);
      if (listRoles !== undefined)
        return (
          listRoles.includes("ROLE_ADMIN") || listRoles.includes("ROLE_MANAGER")
        );
      return false;
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
  },
};
</script>

<style scoped>
#logo {
  width: 150px;
  margin-left: 20px;
  margin-right: 20px;
}

.nav-link {
  color: rgba(255, 255, 255);
}

#search-button-navbar {
  background-color: #febd69;
  border-color: #febd69;
  border-top-right-radius: 2px;
  border-bottom-right-radius: 2px;
}
#nav-cart-count {
  background-color: red;
  color: white;
  border-radius: 50%;
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 15px;
  height: 15px;
  font-size: 15px;
  margin-left: 10px;
}
#cart {
  position: relative;
}
</style>
