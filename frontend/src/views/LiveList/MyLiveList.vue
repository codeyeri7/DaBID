<template>
<div style="font-family: 'InfinitySans-RegularA1';">
    <!-- card -->
    <div class="main-card">
      <v-card class="mx-auto" width="500">
        <v-container fluid>
          <div>
            <span><b>My Live List</b></span>
          </div>
          <v-row dense>
            <v-col v-for="(live, idx) in this.lives" :key="idx" :live="live">
              <v-card class="section1" height="300px">
                <!-- Image -->
                <v-img
                  :src="live.src"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding: 40px"
                  @click="goDetail(live.id)"
                  v-if="live.liveTitle" 
                  title="상세정보 확인을 확인해보세요"
                >
                  <!-- image 안 title -->
                  <v-card-title class="subtitle-style"></v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-title class="text-subtitle-1 my-1">{{ live.prdName }}</v-card-title>
                <v-card-subtitle class="text-subtitle-4 my-1">시작가 | {{ live.prdPriceStart }}</v-card-subtitle>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
        <!-- <div>
          <button class="btn btn-large btn-primary me-1" @click="goLive1">
            방송 켜기
          </button>
          <button class="btn btn-large btn-primary ms-1" @click="goLive2">
            방송 참여하기
          </button>
        </div> -->
        <hr />

        <v-container fluid>
          <div>
            <span><b>방송 예정</b></span>
            <span align="right" style="padding-left: 230px">더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in cards2" :key="card.title" :cols="card.flex">
              <v-card class="section2">
                <!-- Image -->
                <v-img
                  src="@/assets/wallet.png"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding-left: 20px"
                >
                  <!-- image 안 title -->
                  <v-card-title v-text="card.title"></v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-title class="text-subtitle-1 my-1" v-text="card.livetitle"></v-card-title>
                <v-card-subtitle class="text-subtitle-4 my-1" v-text="card.text"></v-card-subtitle>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
        <hr />

        <v-container fluid>
          <div>
            <span><b>방송 종료</b></span>
            <span align="right" style="padding-left: 230px">더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in cards3" :key="card.title" :cols="card.flex">
              <v-card class="section3">
                <!-- Image -->
                <v-img
                  src="@/assets/prada.png"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding-left: 25px"
                >
                  <!-- image 안 title -->
                  <v-card-title v-text="card.title"></v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-title class="text-subtitle-1 my-1" v-text="card.livetitle"></v-card-title>
                <v-card-subtitle class="text-subtitle-4 my-1" v-text="card.text"></v-card-subtitle>
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