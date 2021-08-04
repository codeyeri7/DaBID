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
        localStorage.removeItem('userName')
      },
      async login() {
        const googleUser = await this.$gAuth.signIn()
        console.log('googleUser', googleUser)
        this.isLogin = this.$gAuth.isAuthorized
        // id_token에 저장하고 서버에 보내기 
        this.id_token = googleUser.getAuthResponse().id_token;
        console.log('보내는 id token', this.id_token)
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
      // 응답 온거 vuex에 저장
      .then((res) => {
        console.log('server에서 온 응답', res.data)
        this.isLogin = true 
        localStorage.setItem('userName', res.data.userName)
        localStorage.setItem('jwt', res.data.id_token)
        console.log(localStorage)
        this.$router.push({ name: 'main' })
      }) 
      .catch((err) => {
        console.log('오류발견', err)
      })      
    },
  }
}
</script>

<style scoped>

</style>