<template>
  <div id="kor-font">
    <div class="main-card">
      <div class="mx-auto">
        <v-container fluid>
          <div>
            <span id="eng-font" style="font-size:20px; padding-bottom:40px"><b>My Wish List</b></span>
          </div>
          <v-row dense class="mt-2">
            <MyWishLiveCard v-for="(live, idx) in lives" :key="idx" :live="live"/>
          </v-row>
        </v-container>
      </div>
    </div>
  </div>
</template>

<script>
import MyWishLiveCard from "../../components/MyWishLiveCard.vue";
import rest from "../../js/httpCommon.js";

export default {
  name: "MyWishList",
  components: {
    MyWishLiveCard,
  },
  data: function () {
    return {
      lives: [],
    };
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem("jwt");
      const config = {
        Authorization: ` Bearer ${jwtToken}`,
      };
      return config;
    },
    getWishList: function () {
      rest.axios({
        method: "get",
        url: "/dabid/wish/wishLive",
        headers: this.setToken(),
      })
        .then((res) => {
          this.lives = res.data;
          console.log(this.lives)
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getWishList();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style>
</style>