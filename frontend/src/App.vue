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
          <img src="@/assets/LOGO.png" width="40" class="pt-2">
        </RouterLink>

        <v-spacer></v-spacer>
        <img src="@/assets/menu.png" alt="menubar" style="width:35px">
        <!-- 지운 검색창 --> 
        <!-- <v-text-field
          hide-details
          prepend-icon="mdi-magnify"
          single-line
          placeholder="Search"
        ></v-text-field> -->
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
          <img src="@/assets/Home.png" width="40" style="margin-left:50px; margin-top:7px">
        </RouterLink>
        <RouterLink :to="{ name: '' }">
          <img src="@/assets/Chat.png" width="30" style="margin-left:35px;margin-top:7px">
        </RouterLink>
        <RouterLink :to="{ name: 'MyLiveList' }">
          <img src="@/assets/live.png" width="40" style="margin-left:35px;margin-top:7px">
        </RouterLink>
        <RouterLink :to="{ name: 'MyPage' }">
          <img src="@/assets/profile.png" width="30"  style="margin-left:35px;margin-top:7px">
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
  }
}
</script>

<style scope>

footer{ 
  position:fixed; 
  left:0px; 
  bottom:0px; 
  height:50px; 
  width:100%; 
  background:rgb(240, 223, 221); 
  color: black;
  border-radius: 30% 30% 0% 0%;
  }
</style>