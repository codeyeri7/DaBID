<template>
  <div class="container main-back">
    <h3 class="gold-color ml-5" id="eng-font">Review List</h3>
    <v-col v-for="review in reviews" :key="review">
      <v-card color="gray px-2 py-2" tile elevation="1">
        <div class="review-content d-flex justify-space-between pb-2 px-2" id="kor-font" :style="{'font-size':fontSize1+'px'}">
          <v-card-subtitle1>
            {{ review.writerName }} 
          </v-card-subtitle1>
          <v-card-subtitle2>
            {{ review.review.reviewDate.slice(0, 10) }}
            {{ review.review.reviewDate.slice(11, 16) }}
          </v-card-subtitle2>
        </div>
          <v-card-subtitle1 class="px-2 review-content" id="kor-font" :style="{'font-size':fontSize2+'px'}">
            {{ review.review.reviewContent }}
          </v-card-subtitle1>
      </v-card>
    </v-col>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js";

export default {
  name: "ReviewList",
  data: function () {
    return {
      userId: null,
      reviews: [],
      fontSize1: 14,
      fontSize2: 16
    };
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
      console.log(this.userId);
      rest
        .axios({
          method: "get",
          url: `/dabid/users/checkReview/${this.userId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          this.reviews = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.userId = this.$route.params.userId
      this.getReviews();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style scoped>
#card-subtitle1 {
  font-size: 2rem;
}
#card-subtitle2 {
  font-size: 0.75rem;
}
.review-content {
  color: #dfb772;
}
.container {
  margin-bottom: 60px;
}
</style>