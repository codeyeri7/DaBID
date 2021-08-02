<template>
  <v-app>
    <v-app-bar
      app
      flat
      color="white"
      dark
    >
      <RouterLink :to="{ name: 'Main' }">
        <img src="@/assets/Dabid_logo_80.png" width="40" class="pt-2">
      </RouterLink>
      <v-spacer></v-spacer>
      <!-- router 아직 라이브랑 공지사항 안 만듦 -->
      <RouterLink :to="{ name: 'MyPage' }">
        <img src="@/assets/live.png" width="60" class="pt-2">
      </RouterLink>
      <RouterLink :to="{ name: '' }">
        <img src="@/assets/notification.png" width="33" class="pt-4 pb-2">
      </RouterLink>
      <RouterLink :to="{ name: 'Login' }">
        <img src="@/assets/add-user.png" width="33" class="pt-4 pb-2 ml-3">
      </RouterLink>
    </v-app-bar>
    <!-- 메인페이지가 routerview default -->
    <v-content>
      <RouterView></RouterView>
    </v-content>
  </v-app>
</template>

<script>
// import { OpenVidu } from 'openvidu-browser';

export default {
  name: 'App',
  props: {
    msg: String
  },
  created() {
    window.onSignIn = this.onSignIn;
  },
  methods: {
    onSignIn(googleUser){
      const profile = googleUser.getBasicProfile()
      console.log('ID Token: ', googleUser.getAuthResponse().id_token)
      console.log('ID: '+ profile.getId())
      console.log('Name: '+ profile.getName())
      console.log('Image URL: '+ profile.getImageUrl())
      console.log('Email: '+ profile.getEmail())
    },
  }
}
</script>

<style scope>
#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /* color: #2c3e50; */

  /* tool bar와 carousel 간격 */ 
  margin-top: 2px;
}
/* header와 content 분리*/ 
.wrapper {
    width:100%;
    position:fixed;
    z-index:10;
    background:inherit;
}

.bottom-wrapper {
    width:100%;
    padding-top:70px;
    z-index:5;
    overflow:auto;
}
</style>