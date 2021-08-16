<template>
  <div>
    <h3>Review List</h3>
    <v-col
      v-for="review in reviews"
      :key="review">
      <v-card>
        <div>
          <v-card-title>
            {{ review.reviewContent }}
          </v-card-title>
          <v-card-subtitle>
            {{ review.reviewDate.slice(0,10) }}
            {{ review.reviewDate.slice(11,16) }}
          </v-card-subtitle>
          <v-card-subtitle>
            {{ review.reviewWriter }}
          </v-card-subtitle>
        </div>
      </v-card>
    </v-col>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js";

export default {
  name: 'ReviewList',
  data: function () {
    return {
      reviews: [],
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
      const userId = this.$route.params.userId
      console.log(userId)
      rest
        .axios({
          method: "get",
          url: `/dabid/users/checkReview/${userId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          this.reviews = res.data;
          console.log(this.reviews);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // getUser: function () {
    //   const writer = this.reviews.
    //   console.log(writer)
    //   rest
    //     .axios({
    //       method: "get",
    //       url: `dabid/users/${writer}`,
    //       headers: this.setToken()
    //     })
    //     .then((res) => {
    //       this.writerId = res.data;
    //       console.log(this.writerId);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     })
    // }
  },
   created: function () {
    if (localStorage.getItem("jwt")) {
      this.getReviews();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
}
</script>

<style scoped>

</style>