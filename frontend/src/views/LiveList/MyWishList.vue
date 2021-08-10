<template>
  <div style="font-family: 'InfinitySans-RegularA1'">
    <div class="main-card">
      <v-card class="mx-auto" width="500">
        <v-container fluid>
          <div>
            <span
              style="
                font-family: 'PT Serif', serif;
                font-size: 20px;
                margin-bottom: 20px;
              "
              ><b>My Wish List</b></span
            >
          </div>
          <v-row dense>
            <MyLiveListCard
              v-for="(live, idx) in lives"
              :key="idx"
              :live="live"
            />
          </v-row>
        </v-container>
      </v-card>
    </div>
  </div>
</template>

<script>
import MyLiveListCard from "../../components/MyLiveListCard.vue";
import rest from "../../js/httpCommon.js";

export default {
  name: "MyWishList",
  components: {
    MyLiveListCard,
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
        Authorization: `JWT ${jwtToken}`,
      };
      return config;
    },
    getWishList: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/wish/" + localStorage.getItem("userId") + "/wishLive",
          headers: this.setToken(),
        })
        .then((res) => {
          this.lives = res.data;
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