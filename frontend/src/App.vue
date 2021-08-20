<template>
  <div class="main-back">
    <v-app>
      <v-app-bar
        app
        flat
        light
        id="app-bar"
        v-if="isLogin"
      >
        <RouterLink :to="{ name: 'Main' }">
          <img src="@/assets/logoa.png" width="42" class="pt-2">
        </RouterLink>
        
        <v-spacer></v-spacer>
      </v-app-bar>

      <!-- 메인페이지가 routerview default -->
      <v-content>
        <RouterView></RouterView>
      </v-content>

        <div v-if="isLogin">
          <footer
            app
            flat  
            light 
          >
            <RouterLink :to="{ name: 'Main' }">
              <img src="@/assets/Home.png" width="35" style="margin-left:1.2rem;">
            </RouterLink>
            <RouterLink :to="{ name: 'AllLiveList' }">
              <img src="@/assets/myLive.png" width="35">
            </RouterLink>
            <RouterLink :to="{ name: 'Chatlist' }">
              <img src="@/assets/chat.png" width="30">
            </RouterLink>
            <RouterLink :to="{ name: 'MyPage' }">
              <img src="@/assets/profile.png" width="30"  style="margin-right:1.2rem">
            </RouterLink>
          </footer>
         </div>
    </v-app>
  </div>
</template>

<script>

export default {
  name: 'App',
  data: function () {
    return {
      isLogin: false,
      userName: null,
      drawer: false,
      group: null,
    }
  },
   methods: {
    logout: function () {
      this.isLogin = false
      localStorage.removeItem('jwt')
      localStorage.removeItem('userName')
      this.$router.push({ name: 'Login' })
    },
    onclick: function () {
      window.open('https://thecheat.co.kr/rb/?r=home&mod=_thecheat_validity_account', '_blank')
    },
  },
  async mounted() {
    try {
      const token = localStorage.getItem('jwt')
      if (token) {
        this.isLogin = true
        this.userName = localStorage.getItem('userName')
      }
    }
    catch (error) {
      console.log(error)
    }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Lora&display=swap');
@import url('https://fonts.googleapis.com/css2?family=PT+Serif&display=swap');

footer{ 
  /* border-top: 1px solid #292938; */
  position:fixed; 
  left:0px; 
  bottom:0px; 
  height:50px; 
  width:100%; 
  background:#3c3f44; 
  display:flex; 
  justify-content:space-between;
  align-items: center;
}
.v-toolbar__content, .v-toolbar__extension {
}
#eng-font {
  font-family: 'Lora', serif;
}
#kor-font {
  font-family: 'InfinitySans-RegularA1';
}

@font-face {
  font-family: "InfinitySans-RegularA1";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#app-bar {
  background-color: #3c3f44;
}
.gold-color {
  color: #dfb772;
}


</style>