<template>
<div class="container">
  <button class="btn btn-danger ms-3" @click="logout()">Logout</button>
  <v-card
    class="mx-auto elevation-20"
    style="max-width: 80%; margin-top:50px"
  >
    <v-row justify="space-between">
      <v-col>
        <v-card-title>
          <div>
            <div class="text-h5">
              {{ userName}}
            </div>
          </div>
        </v-card-title>
      </v-col>
    </v-row>
    <v-divider dark></v-divider>
    <v-card-actions class="pa-4">
      check your points 
      <v-spacer></v-spacer>
      <span class="black--text text--lighten-2 text-caption mr-2">
        ({{ rating }})
      </span>
      <v-rating
        v-model="rating"
        background-color="white"
        color="yellow accent-4"
        dense
        half-increments
        hover
        size="15"
      ></v-rating>
    </v-card-actions>
  </v-card>
	</div>
</template>

<script>
export default {
  name: 'Mypage',
  data () {
      return {
        userName: null,
        rating: 2.7,
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
      localStorage.removeItem('userId')
      localStorage.removeItem('jwt')
      localStorage.removeItem('userName')
      this.$router.push({ name: 'main' })
      this.$router.go();
    }
  },
  mounted: async function () {
    if (localStorage.getItem('jwt')) {
      // console.log(localStorage)
      this.userName = localStorage.getItem('userName')
    } else {
      this.$router.push({ name: 'Login' })
    }
  },
}
</script>

<style>
</style>