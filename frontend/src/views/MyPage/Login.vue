<template>
  <div>
    <div>
      <v-container align-center>
        <img src="@/assets/Dabid_logo.png" width="150px" class="mb-5" style="margin-top: 100px"><br>
          <div class="card-body">
            <h3 class="card-title">로그인을 시도해주세요</h3>
            <h6 class="card-subtitle mb-2 text-muted">you can login within <b>5</b> seconds</h6>
            <button @click="login()"><img src="@/assets/google_login.png" alt="google_login_img" style="width:80%"></button> 
          </div>
      </v-container>
    </div>
  </div>
</template>

<script>
// import axios from "axios";
import rest from "../../js/httpCommon.js";
export default {
  name: "Login",
  data () {
      return {
        isLogin:false,
        id_token: null,
      }
    },
    created() {
      window.onSignIn = this.onSignIn;
    },
    methods: {
      async logOut(){
        const result = await this.$gAuth.signOut()
        this.isLogin = false
        console.log('result', result)
      },
      async login() {
        const googleUser = await this.$gAuth.signIn()
        // console.log('googleUser', googleUser)
        // console.log('ID: '+ googleUser.getId())
        // console.log('Base Profile: '+ googleUser.getBasicProfile())
        // console.log('Auth Response: '+ googleUser.getAuthResponse())
        this.isLogin = this.$gAuth.isAuthorized
        // 서버에 토큰 보내기 
        this.id_token = googleUser.getAuthResponse().id_token;
        console.log(this.id_token)
        this.sendToken()
      },
      // 서버에 id_token 보내기 
      sendToken: function () {
        rest 
          .axios({
            method: "post",
            url: "/auth/login",
            data: {
              id_token: this.id_token
            }
          })
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
      }
    },
  }

</script>

<style scope>

</style>