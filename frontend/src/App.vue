<template>
  <v-app>
    <!-- <div class="v-application">
      <Navbar v-if="!isChecked"></Navbar>
    </div> -->
    <v-app-bar
      app
      flat
      color="white"
      dark
      v-if="!isChecked"
    >
      <RouterLink :to="{ name: 'Main' }">
        <img src="@/assets/LOGO.jpg" width="40" class="pt-2">
      </RouterLink>
      <v-spacer></v-spacer>
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
// import Navbar from '@/components/Navbar.vue'
// import { OpenVidu } from 'openvidu-browser';

export default {
  name: 'App',
  // components: {
  //   Navbar
  // },
  // props: {
  //   msg: String
  // },
  // created() {
  //   window.onSignIn = this.onSignIn;
  // },
  // methods: {
  //   onSignIn(googleUser){
  //     const profile = googleUser.getBasicProfile()
  //     console.log('ID Token: ', googleUser.getAuthResponse().id_token)
  //     console.log('ID: '+ profile.getId())
  //     console.log('Name: '+ profile.getName())
  //     console.log('Image URL: '+ profile.getImageUrl())
  //     console.log('Email: '+ profile.getEmail())
  //   },
  // }
  isChecked: false,
  created() {
    if(document.location.pathname === '/login'){
      this.isChecked = true;
      console.log(this.isChecked)
    }
  }
}
</script>

<style scope>
/* header와 content 분리*/ 
/* .wrapper {
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
} */
</style>