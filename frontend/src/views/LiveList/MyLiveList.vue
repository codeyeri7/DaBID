<template>
  <div id="kor-font">
    <div class="main-card">
      <div class="mx-auto">
        <v-tabs
          v-model="tabs"
          right
          id="tabs"
        >
          <v-tab>방송예정</v-tab>
          <v-tab>방송종료</v-tab>

          <v-tabs-items v-model="tabs">
            <v-tab-item style="background-color:#151618">
              <v-container fluid style="background-color:#151618">
                <div class="mb-3">
                  <span class="gold-color" id="kor-font" style="font-size:20px;"><b>{{ userName }}의 라이브</b></span>
                </div>
                <v-row dense>
                  <MyLiveListCard v-for="(live, idx) in will_lives" :key="idx" :live="live"/>
                </v-row>
              </v-container>
            </v-tab-item>
            <v-tab-item>
              <v-container fluid style="background-color:#151618">
                <div class="mb-3">
                  <span class="gold-color" id="kor-font" style="font-size:20px;"><b>{{ userName }}의 종료된 라이브</b></span>
                </div>
                <v-row dense class="mt-2">
                  <MyLiveListEnd v-for="(live, idx) in end_lives" :key="idx" :live="live"/>
                </v-row>
              </v-container>
            </v-tab-item>
          </v-tabs-items>
        </v-tabs>
      </div>
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
      Info: null,
      lives: [],
      tabs: null,
      userId: null,
      userName: null,
      will_lives: [],
      end_lives: []
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
    getLiveList: function (userId) {
      rest.axios({
        method: 'get',
        url: `/dabid/users/${userId}`,
        headers: this.setToken()
      })
        .then((res) => {
          this.Info = res.data
          this.lives = res.data.liveList
          this.userName = this.Info.user.userName
          //라이브 분류 작업 
          for (var i=0; i < this.lives.length; i++) {
             const one_live = this.lives[i]
              if (one_live.liveStatus.liveStatus == 0) {
                this.will_lives.push(one_live)
              }
              else if (one_live.liveStatus.liveStatus == 2) {
                this.end_lives.push(one_live)
              }
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
  created: function () {
    if (localStorage.getItem('jwt')) {
      this.userId = this.$route.params.userId
      this.getLiveList(this.userId)
    } else {
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style scoped>
</style>