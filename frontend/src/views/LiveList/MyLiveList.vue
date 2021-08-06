<template>
<div style="font-family: 'InfinitySans-RegularA1';">
    <!-- card -->
    <div class="main-card">
      <v-card class="mx-auto" width="500">
        <v-container fluid>
          <div>
            <span style="font-family: 'PT Serif', serif;font-size:20px; margin-bottom:20px"><b>My Live List</b></span>
          </div>
          <v-row dense>
            <v-col v-for="(live, idx) in this.lives" :key="idx" :live="live" :cols="6">
              <v-card class="section1" height="300px">
                <!-- Image -->
                <v-img
                  src="@/assets/prada.png"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding: 40px"
                  @click="goDetail(live.id)"
                  v-if="live.liveTitle" 
                  title="상세정보 확인을 확인해보세요"
                >
                  <!-- image 안 title -->
                  <v-card-title class="subtitle-style" style="margin-left:15px; color:red">❤</v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-subtitle class="text-subtitle-4">{{ live.prdName }}</v-card-subtitle>
                <v-card-subtitle class="text-subtitle-4">
                  시작가 | {{ live.prdPriceStart | comma }}원
                </v-card-subtitle>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
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
  filters: {
      comma: function (value) {
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
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