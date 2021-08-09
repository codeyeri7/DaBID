<template>
  <div style="font-family: 'InfinitySans-RegularA1';">
    <div class="main-card">
      <v-card class="mx-auto" width="500">
        <v-container fluid>
          <div>
            <span style="font-family: 'PT Serif', serif;font-size:20px; margin-bottom:20px"><b>My Live List</b></span>
          </div>
          <v-row dense>
            <MyLiveListCard v-for="(live, idx) in lives" :key="idx" :live="live"/>
          </v-row>
        </v-container>
      </v-card>
    </div>
  </div>
</template>


<script>
import MyLiveListCard from '../../components/MyLiveListCard.vue'
import rest from "../../js/httpCommon.js"

export default {
  name: 'MyLiveList',
  components: { MyLiveListCard },
  data: function () {
    return {
      lives: [],
    }
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${jwtToken}`
      }
      return config
    },
    getLiveList: function () {
      rest.axios({
        method: 'get',
        url: '/dabid/users/'+localStorage.getItem('userId')+'/myLive',
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
    remove: function (prdId) {
      rest.axios({
        method: 'delete',
        url: `/dabid/live/${prdId}`,
      })
        .then((res) => {
          this.getLiveList()
          console.log(res)
          alert('해당 게시글이 삭제되었습니다')
        })
        .catch((err) => {
          console.log(err)
        })
    },
    edit: function (live) {
      this.$router.push({ name: 'UpdateMyLiveList', params: {live: live} });
    }
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