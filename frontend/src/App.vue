<template>
  <div id="app">
     <div id="nav" class="sticky-top" style="background-color:white">
      <span v-if="isLogin" class="px-3 py-1">
        <!-- 원래 버전-->
        <RouterLink :to="{ name: 'Main' }">
          <img src="@/assets/LOGO.jpg" width="40" class="pt-2">
        </RouterLink>
        <RouterLink :to="{ name: 'MyPage' }">
          <img src="@/assets/live.png" width="60" class="pt-2">
        </RouterLink>
        <RouterLink :to="{ name: '' }">
          <img src="@/assets/notification.png" width="33" class="pt-4 pb-2">
        </RouterLink>
        <RouterLink :to="{ name: 'Login' }">
          <img src="@/assets/add-user.png" width="33" class="pt-4 pb-2 ml-3">
        </RouterLink>
        <router-link @click.native="logout" to="#" class="mx-3">로그아웃</router-link>
        <!-- 원래 버전-->
      </span>

      <span v-else>
      </span>
    </div>
    <RouterView @login="isLogin=true"/>
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