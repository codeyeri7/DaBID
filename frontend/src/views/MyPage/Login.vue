<template>
  <div class="container" id="back">
    <div>
      <v-container align-center>
        <img
          src="@/assets/logo2.png"
          width="150px"
          class="mb-5 logo"
          style="margin-top: 120px"
        /><br />
        <div class="card-body">
          <div v-if="isLogin">
            <img
              src="@/assets/loading.png"
              alt="loading"
              style="margin-top: 60px; width: 90px; text-align: center"
            />
          </div>
          <div v-else class="login-body">
            <h4 style="margin-top: 60px" id="eng-font">
              <b>Get your Luxury</b>
            </h4>
            <img
              src="@/assets/google_login.png"
              alt="google_login_img"
              @click="login"
              style="width: 70%"
            />
          </div>
          <h5 style="margin-top: 115px" class="dabid" id="eng-font">DaBID</h5>
        </div>
      </v-container>
    </div>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js";
export default {
  name: "Login",
  data() {
    return {
      isLogin: false,
      id_token: null,
    };
  },

  created() {
    window.onSignIn = this.onSignIn;
  },

  methods: {
    async login() {
      const googleUser = await this.$gAuth.signIn();
      // console.log('googleUser', googleUser)
      this.isLogin = this.$gAuth.isAuthorized;
      // id_token에 저장하고 서버에 보내기
      this.id_token = googleUser.getAuthResponse().id_token;
      // console.log('보내는 id token', this.id_token)
      this.sendToken();
    },
    // 서버에 id_token 보내기
    sendToken: function () {
      rest
        .axios({
          method: "post",
          url: "/auth/login",
          data: {
            id_token: this.id_token,
          },
        })
        .then((res) => {
          // console.log('server에서 온 응답', res.data)
          localStorage.setItem("userName", res.data.userName);
          localStorage.setItem("userId", res.data.userId);
          localStorage.setItem("jwt", res.data.accessToken);
          // console.log(localStorage)
          this.isLogin = true;
          this.$router.push({ name: "Main" });
          this.$router.go();
        })
        .catch((err) => {
          console.log("오류발견", err);
        });
    },
  },
};
</script>

<style scoped>
#back {
  background-image: url("background.jpg");
  background-size: cover;
  height: 100%;
}
.dabid {
  font-size: 1rem;
}
.dabid h5 {
  color: #dfb772;
}
#eng-font b {
  color: #dfb772;
}
.container {
  text-align: center;
}
</style>