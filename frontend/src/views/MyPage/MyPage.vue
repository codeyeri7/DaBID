<template>
<div class="container">
  {{ userName }}
  <button class="btn btn-danger ms-3" @click="logout()">Logout</button>
	</div>
</template>

<script>
export default {
  name: 'Mypage',
  data () {
      return {
        userName: null,
      }
    },
  methods: {
    setToken: function() {
      const jwtToken = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${jwtToken}`
      }
      return config
    },
    async logout() {
    // google 로그아웃 
      const result = await this.$gAuth.signOut()
      console.log('result', result)
      console.log('logout성공이닷')
      // localstorage 처리 
      this.isLogin = false
      localStorage.removeItem('jwt')
      localStorage.removeItem('userName')
      this.$router.push({ name: 'main' })
      this.$router.go();
    }
  },
  mounted: async function () {
    if (localStorage.getItem('jwt')) {
      console.log(localStorage)
      this.userName = localStorage.getItem('userName')
    } else {
      this.$router.push({ name: 'Login' })
    }
  },
}
</script>

<style>
</style>