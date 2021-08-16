<template>
  <div>

  </div>
</template>

<script>
import rest from "../../js/httpCommon.js";

export default {
  name: 'ReviewList',
  data: function () {
    return {
      reviews: null,
      userId: null,
    }
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${jwtToken}`,
      };
      return config;
    },
    getReviews: function () {
      rest
        .axios({
          method: "get",
          url: `/dabid/users/checkReview/${userId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          this.person = res.data;
          console.log(res);
          // this.$router.push({ name: "ReviewList" });
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
   created: function () {
    if (localStorage.getItem("jwt")) {
      this.userId = this.$route.params.userId
      this.getReviews();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
}
</script>

<style scoped>

</style>