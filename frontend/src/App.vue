<template>
  <div>
    <v-app>
      <v-app-bar
        app
        flat
        color="white"  
        light
        v-if="isLogin"
      >
        <RouterLink :to="{ name: 'Main' }">
          <img src="@/assets/LOGO.png" width="42" class="pt-2">
        </RouterLink>

        <!-- <v-spacer></v-spacer> -->
       
      </v-app-bar>
      <!-- 메인페이지가 routerview default -->
      <v-content>
        <RouterView></RouterView>
      </v-content>
    </v-app>
    <div v-if="isLogin" class="row" style="margin-top:60px">
      <footer
        app
        flat  
        light
      >
        <RouterLink :to="{ name: 'Main' }">
          <img src="@/assets/Home.png" width="40" style="margin-left:1.2rem;">
        </RouterLink>
        <RouterLink :to="{ name: 'AllLiveList' }">
          <img src="@/assets/live.png" width="40">
        </RouterLink>
        <RouterLink :to="{ name: 'Chatlist' }">
          <img src="@/assets/Chat.png" width="30">
        </RouterLink>
        <RouterLink :to="{ name: 'MyPage' }">
          <img src="@/assets/profile.png" width="30"  style="margin-right:1.2rem">
        </RouterLink>
      </footer>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  data: function () {
    return {
      isLogin: false,
      userName: null,
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
footer{ 
  border-top: 1px solid #292938;
  position:fixed; 
  left:0px; 
  bottom:0px; 
  height:50px; 
  width:100%; 
  background:rgb(255, 255, 255); 
  display:flex; 
  justify-content:space-between;
  align-items: center;
  }
.v-toolbar__content, .v-toolbar__extension {
  border-bottom: 1px solid #292938;
}

#eng-font {
  font-family: 'Lora', serif;
}

@font-face {
  font-family: "InfinitySans-RegularA1";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#kor-font {
  font-family: "InfinitySans-RegularA1";
}
</style>