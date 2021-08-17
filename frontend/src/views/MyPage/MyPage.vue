<template>
  <div class="body main-back">
    <MyProfile :Person ="person"/>
      <v-card color="secondary" class="mx-auto" max-width="290">
          <v-simple-table>
          <template v-slot:default>
            <tbody style="background-color:#3c3f44;">
              <tr
                v-for="item in items"
                :key="item.name"
                @click="menuActionClick(item.action)"
              >
                <td id="eng-font" class="gold-color">{{ item.text }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
    </v-card>

    <v-card color="secondary" class="mx-auto mt-5" max-width="290">
          <v-simple-table>
          <template v-slot:default>
            <tbody style="background-color:#3c3f44;">
              <tr @click="logout()">
                <td class="logout" id="eng-font"><b>Log out</b></td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
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
      // userId: this.person.userId,
      dialog: false,
      fontSize: 8,
      person: null,
      userId: null,
      items: [
        {
          src: require("@/assets/myLive.png"),
          text: "My B-Live",
          action: "goMyLive",
        },
        {
          src: require("@/assets/mywish.png"),
          text: "My Wish Live",
          action: "goWishLive",
        },
        {
          src: require("@/assets/mynotice.png"),
          text: "Notice",
          action: "goNotice",
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
          console.log(this.person)
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
        this.$router.push({ name: "ReviewList", params: { userId: `${this.person.userId}`} });
      } else if (action === "goNotice") {
        this.$router.push({ name: "NoticeList" });
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
.body {
  background-color: #151618;
}
.card-text {
  display: flex;
  flex-direction: column;
}
.text-center {
  color:bisque
}
.logout {
  color:red;
  text-align: center;
}
</style>