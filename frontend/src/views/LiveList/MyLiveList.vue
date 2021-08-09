<template>
  <div style="font-family: 'InfinitySans-RegularA1';">
    <div class="main-card">
      <v-card class="mx-auto" width="500">
        <v-tabs
          v-model="tabs"
          color="deep-purple accent-4"
          right
        >
          <v-tab>방송예정</v-tab>
          <v-tab>방송종료</v-tab>

          <v-tabs-items v-model="tabs">
            <v-tab-item>
              <v-container fluid>
                <div>
                  <span style="font-family: 'PT Serif', serif;font-size:20px; margin-bottom:20px"><b>My Live List - 방송예정</b></span>
                </div>
                <v-row dense>
                  <MyLiveListCard v-for="(live, idx) in lives" :key="idx" :live="live"/>
                </v-row>
              </v-container>
            </v-tab-item>
            <v-tab-item>
              <v-container fluid>
                <div>
                  <span style="font-family: 'PT Serif', serif;font-size:20px; margin-bottom:20px"><b>My Live List - 방송종료</b></span>
                </div>
                <v-row dense>
                  <MyLiveListEnd v-for="(live, idx) in lives" :key="idx" :live="live"/>
                </v-row>
              </v-container>
            </v-tab-item>
          </v-tabs-items>
        </v-tabs>
      </v-card>
    </div>
  </div>
</template>


<script>
import MyLiveListCard from '../../components/MyLiveListCard.vue'
import MyLiveListEnd from '../../components/MyLiveListEnd.vue'
import rest from "../../js/httpCommon.js"

export default {
  name: 'MyLiveList',
  components: { 
    MyLiveListCard,
    MyLiveListEnd
  },
  data: function () {
    return {
      lives: [],
      tabs: null,
    }
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${jwtToken}`
      }
      return config
    },
    getLiveList: function () {
      rest.axios({
        method: 'get',
        url: '/dabid/users/myLive',
        headers: this.setToken()
      })
        .then((res) => {
          this.lives = res.data
          console.log(this.lives)
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
  created: function () {
    if (localStorage.getItem('jwt')) {
      this.getLiveList()
    } else {
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style scoped>
</style>