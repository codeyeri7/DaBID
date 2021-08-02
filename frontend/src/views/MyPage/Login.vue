<template>
  <div>
    <div>
      <v-container align-center>
        <img src="@/assets/Dabid_logo.png" width="150px" class="mb-5" style="margin-top: 100px"><br>
          <div class="card-body">
            <h3 class="card-title">로그인을 시도해주세요</h3>
            <h6 class="card-subtitle mb-2 text-muted">you can login within <b>5</b> seconds</h6>
            <a href="http://localhost:8080/auth/GOOGLE"><img src="@/assets/google_login.png" alt="google_login_img" style="width:80%"></a> 
          </div>
      </v-container>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'
export default {
  name: "Login",
  data () {
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
  },
}
  
</script>

<style scope>

</style>