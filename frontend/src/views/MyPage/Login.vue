<template>
  <div class="container" id="back">
    <div>
      <v-container align-center>
        <img src="@/assets/LOGO.png" width="150px" class="mb-5" style="margin-top: 120px; margin-left:90px"><br>
          <div class="card-body">
            <div v-if="isLogin">
              <img src="@/assets/loading.png" alt="loading" style="margin-left:113px;margin-top:60px;width:90px">
            </div>
            <div v-else>
              <h5 style="margin-left:113px;margin-top:60px;font-family: 'PT Serif', serif;">Get your luxury</h5>
              <button @click="login()"><img src="@/assets/google_login.png" alt="google_login_img" style="width:80%"></button> 
            </div>
            <h5 style="margin-top:160px;margin-left:130px;font-family: 'PT Serif', serif;">DaBID</h5>
          </div>
      </v-container>
    </div>
  </div>
</template>

<script>
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
      async login() {
        const googleUser = await this.$gAuth.signIn()
        // console.log('googleUser', googleUser)
        this.isLogin = this.$gAuth.isAuthorized
        // id_token에 저장하고 서버에 보내기 
        this.id_token = googleUser.getAuthResponse().id_token;
        // console.log('보내는 id token', this.id_token)
        this.sendToken()
      },
      // 서버에 id_token 보내기 
      sendToken: function () {
        rest.axios({
          method: "post",
        url: "/auth/login",
        data: {
          id_token: this.id_token
        }
      })
      .then((res) => {
        // console.log('server에서 온 응답', res.data)
        localStorage.setItem('userId', res.data.userId)
        localStorage.setItem('userName', res.data.userName)
        localStorage.setItem('jwt', res.data.accessToken)
        // console.log(localStorage)
        this.isLogin = true 
        this.$router.push({ name: 'Main' })
        this.$router.go();
      }) 
      .catch((err) => {
        console.log('오류발견', err)
      })      
    },
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Lora&display=swap');
@import url('https://fonts.googleapis.com/css2?family=PT+Serif&display=swap');

#back {
  background-image: url('background.jpg'); 
  background-position: center;
}

</style>