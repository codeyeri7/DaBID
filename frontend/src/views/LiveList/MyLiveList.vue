<template>
  <div class="container" style="background-color:red">
    <h1>My Live List</h1>
    <div class="row">
      <div v-for="(live, idx) in this.lives"
      :key="idx"
      :live="live">
       <div class="card my-2" style="background-color:white;">
          <div class="card-imgTitle" @click="goDetail(live.id)" v-if="live.liveTitle" title="상세정보 확인을 확인해보세요">
            <img v-bind:src="live.src" class="img-fluid" alt="live_thumbnail" style="height: 400px">
            <div class="card-body pt-4">
              <h5 class="card-title"><strong> {{ live.prdName }}</strong></h5>
              <h6 class="mt-4" style="font-size:20px">시작가 | {{ live.prdPriceStart }}</h6>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js"
export default {
  name: 'MyLiveList',
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