<template>
  <div>
    <div>
      <v-container align-center>
        <img src="@/assets/Dabid_logo.png" width="150px" class="mb-5" style="margin-top: 100px"><br>
          <div class="card-body">
            <div v-if="isLogin">
              <h3 class="card-title">이미 로그인한 사용자입니다.</h3>
              <h6 class="card-subtitle mb-2 text-muted">you already join here!</h6>
              <button style="width:80%; background-color:black; color:white;" @click="logout()">Logout</button> 
            </div>
            <div v-else>
              <h3 class="card-title">로그인을 시도해주세요</h3>
              <h6 class="card-subtitle mb-2 text-muted">you can login within <b>5</b> seconds</h6>
              <button @click="login()"><img src="@/assets/google_login.png" alt="google_login_img" style="width:80%"></button> 
            </div>
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
        // google 로그아웃 
        const result = await this.$gAuth.signOut()
        console.log('result', result)
        console.log('logout성공이닷')
        // localstorage 처리 
        this.isLogin = false
        localStorage.removeItem('jwt')
        localStorage.removeItem('user_name')
        this.$router.push({ name: 'Login' })
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
          console.log('post에서 온 응답', res)
          // localStorage.setItem('jwt', res.data.token)
          // localStorage.setItem('used_name', res.data.user_name)
          console.log(localStorage)
          this.isLogin = true 
        })
        .catch((err) => {
          console.log('오류가 나버렸네..', err)
          alert('잘못된 정보입니다.' + '로그인을 다시 시도해주세요')
        })
      }
    },
  }

</script>

<style scope>

</style>