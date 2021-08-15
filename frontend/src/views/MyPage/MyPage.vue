<template>
  <div>
    <MyProfile/>
    <v-card class="mx-auto" max-width="300">
      <v-list>
        <v-list-item-group v-model="model">
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            @click="menuActionClick(item.action)"
          >
            <v-list-item-icon>
              <v-icon v-text="item.icon" style="color: #ff7043"></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title
                v-text="item.text"
                id="item-text"
              ></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
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

          <v-card class="ap-card">
            <v-card-title class="text-h5"
            style="color:#ebe2de; background-color: #292938">
              Accident Policy
            </v-card-title>

            <v-card-text class="card-text" style="margin-top: 2.5rem" id="kor-font">
              다비드는 원활한 거래 연결을 위해<br>
              더치트 계좌 조회 서비스를 지원합니다.
              <v-btn tile class="mt-4 link-button" onclick="window.open('https://thecheat.co.kr/rb/?r=home&mod=_thecheat_validity_account') ">위험 계좌조회 더치트 바로가기</v-btn>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="dialog = false" id="kor-font">
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
          icon: "mdi-monitor",
          text: "My B-live",
          action: "goMyLive",
        },
        {
          icon: "mdi-star",
          text: "My Wish Live",
          action: "goWishLive",
        },
        {
          icon: "mdi-send",
          text: "Chat List",
          action: "goChat",
        },
        {
          icon: "mdi-logout",
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
.card-text {
  display: flex;
  flex-direction: column;
}

</style>