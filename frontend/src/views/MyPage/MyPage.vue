<template>
  <div>
    <!-- <h2 style="margin-left: 40px; font-family: 'Lora', serif">
      {{ person.userName }}'s Profile
    </h2> -->

    <MyProfile/>
    <v-card class="mx-auto" max-width="300">
      <v-btn>
        <img
          v-for="item in items"
          :key="item"
          :src="item.src"
          width="50px"
          height="50px"
          @click="menuActionClick(item.action)"
        ></img>
      </v-btn>
    </v-card>
    <div class="fixedbutton" style="float: right">
      <div class="text-center mr-5">
        <v-dialog v-model="dialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <img
              src="@/assets/warning.png"
              alt="warning"
              style="width: 35px"
              v-bind="attrs"
              v-on="on"
            />
          </template>

          <v-card>
            <v-card-title class="text-h5 grey lighten-2">
              Accident Policy
            </v-card-title>

            <v-card-text style="margin-top: 2.5rem">
              다비드는 원활한 거래 연결을 위해 더치트 계좌 조회 서비스를
              지원합니다.
              <hr />
              <v-btn @click="onClick()">위험 계좌조회 더치트 바로가기</v-btn>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="brown darken-2" text @click="dialog = false">
                확인
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </div>
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

      person: null,
      items: [
        {
          src: require("@/assets/mylive.png"),
          text: "My B-live",
          action: "goMyLive",
        },
        {
          src: require("@/assets/mywish.png"),
          text: "My Wish Live",
          action: "goWishLive",
        },
        {
          src: require("@/assets/mychat.png"),
          text: "Chat List",
          action: "goChat",
        },
        {
          src: require("@/assets/myreview.png"),
          text: "Review List",
          action: "goReview",
        },
        {
          src: require("@/assets/mynotice.png"),
          text: "Notice",
          action: "goNotice",
        },
        {
          src: require("@/assets/myhelp.png"),
          text: "Help",
          action: "goHelp",
        },
        {
          src: require("@/assets/mylogout.png"),
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
          console.log(res);
          // this.$router.push({ name: "ReviewList" });
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
        this.$router.push({ name: "MyLiveList" });
      }
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getProfile();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style scoped>
.btn {
  font-family: "PT Serif";
}
#item-text {
  font-family: "PT Serif", serif;
}
</style>