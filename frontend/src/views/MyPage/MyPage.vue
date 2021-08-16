<template>
  <div>
    <MyProfile :Person ="person"/>
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
import MyProfile from "@/components/MyProfile";
import rest from "../../js/httpCommon.js";

export default {
  name: "Mypage",
  components: {
    MyProfile,
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
          src: require("@/assets/mywish_40.png"),
          text: "Wish",
          action: "goWishLive",
        },
        {
          src: require("@/assets/mychat_40.png"),
          text: "Chat",
          action: "goChat",
        },
        {
          src: require("@/assets/myreview_40.png"),
          text: "Review",
          action: "goReview",
        },
        {
          src: require("@/assets/mynotice_40.png"),
          text: "Notice",
          action: "goNotice",
        },
        {
          src: require("@/assets/myhelp_40.png"),
          text: "Help",
          action: "goHelp",
        },
        {
          src: require("@/assets/bank.png"),
          text: "Check",
          action: "goTheCheat",
        },
        {
          src: require("@/assets/mylogout_40.png"),
          text: "Logout",
          action: "logout",
        },
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
    getProfile: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/users/me",
          headers: this.setToken(),
        })
        .then((res) => {
          this.person = res.data;
          // console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async logout() {
      // google 로그아웃
      const result = await this.$gAuth.signOut();
      console.log("result", result);
      console.log("logout성공이닷");
      // localstorage 처리
      this.isLogin = false;
      localStorage.removeItem("jwt");
      localStorage.removeItem("userName");
      this.$router.push({ name: "main" });
      this.$router.go();
    },
    menuActionClick(action) {
      if (action === "logout") {
        this.logout();
      } else if (action === "goChat") {
        this.$router.push({ name: "ChattingList" });
      } else if (action === "goWishLive") {
        this.$router.push({ name: "MyWishList" });
      } else if (action === "goMyLive") {
        this.$router.push({ name: "MyLiveList", params: { 'userId' : this.userId} });
      } else if (action === "goReview") {
        this.$router.push({ name: "ReviewList" });
      } else if (action === "goNotice") {
        this.$router.push({ name: "Notice" });
      } else if (action === "goHelp") {
        this.$router.push({ name: "Help" });
      } else if (action === "goTheCheat") {
        this.$router.push({ name: "TheCheat" });
      }
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.userId = localStorage.getItem('userId')
      this.getProfile();
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