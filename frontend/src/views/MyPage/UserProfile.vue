<template>
  <div>
    <UserProfile :User="person"/>
    <v-card class="mx-auto" max-width="300">
      <v-row dense>
        <v-col v-for="item in items" :key="item" :cols="3">
          <v-card tile :elevation="0">
            <img
              class="mx-3"
              :src="item.src"
              @click="menuActionClick(item.action)"
            >
            <div id="eng-font">
              <v-card-subtitle class="pt-0 pb-1 text-center" :style="{'font-size':fontSize+'px'}">{{ item.text }}</v-card-subtitle>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import UserProfile from "@/components/UserProfile.vue"
import rest from "../../js/httpCommon.js";

export default {
  name: "Mypage",
  components: {
    UserProfile,
  },
  data: function () {
    return {
      dialog: false,
      fontSize: 8,
      person: null,
      userId: null,
      items: [
        {
          src: require("@/assets/mylive.png"),
          text: "Live",
          action: "goMyLive",
        },
        {
          src: require("@/assets/myreview_40.png"),
          text: "Review",
          action: "goReview",
        },
        // {
        //   src: require("@/assets/mynotice_40.png"),
        //   text: "Notice",
        //   action: "goNotice",
        // },
        {
          src: require("@/assets/myhelp_40.png"),
          text: "Help",
          action: "goHelp",
        }
      ],
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
    getProfile: function (userId) {
      rest
        .axios({
          method: "get",
          url: `/dabid/users/${userId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          this.person = res.data;
          console.log('받아온 정보', this.person);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    menuActionClick(action) {
      if (action === "goMyLive") {
        this.$router.push({ name: "MyLiveList", params: { 'userId' : this.userId} });
      } else if (action === "goReview") {
        this.$router.push({ name: "ReviewList" });
      } else if (action === "goNotice") {
        this.$router.push({ name: "Notice" });
      } else if (action === "goHelp") {
        this.$router.push({ name: "Help" });
      }
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.userId = this.$route.params.userId
      console.log('받아온 ID', this.userId)
      this.getProfile(this.userId);
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style scoped>
.card-text {
  display: flex;
  flex-direction: column;
}

</style>