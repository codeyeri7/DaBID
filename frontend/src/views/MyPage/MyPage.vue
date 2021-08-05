<template>
<div>
  <div class="d-flex justify-space-around mb-6">
    <img 
      :src="Dabidlogo" 
      class="mt-6 mx-6">
    <div>
      <p class="mt-6 my-6 font-weight-black">
        Nick Name
      </p>
      <v-btn icon>
        <v-icon>$pencil</v-icon>
      </v-btn>
    </div>
  </div>
  <div class="small">
    <HorizontalBar :chart-data="datacollection"></HorizontalBar>
    <button @click="fillData()">Randomize</button>
  </div>
  <button class="btn btn-danger" @click="logout()">Logout</button>
	</div>
</template>

<script>
export default {
  name: 'Mypage',
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
    }
  },
  mounted: async function () {
    if (localStorage.getItem('jwt')) {
      console.log(localStorage)
    } else {
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style>
</style>