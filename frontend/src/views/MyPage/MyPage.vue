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
import { mapGetters } from 'vuex'
import Dabidlogo from '@/assets/Dabid_logo_80.png'

export default {
  name: 'Mypage',
  type: 'horizontalBar',
  data() {
      return {
      Dabidlogo : Dabidlogo,
      }
  },
  computed: {
	...mapGetters(['isAuthenticated']),
  },
  methods: {
	redirect() {
		console.log('isAuthenticated :' + this.isAuthenticated)
		if (!this.isAuthenticated) {
			this.$router.push({namee: 'Login'})
		}
	},
	logout() {
		this.$store
          .dispatch("logout", {})
          .then(() => this.redirect())
          .catch(({ message }) => alert(message))
	},
  },
}
</script>

<style>
</style>