<template>
<div>
  <!-- top2 carousel --> 
  <v-carousel
      cycle
      height="230"
      hide-delimiter-background
      show-arrows-on-hover
    >
      <v-carousel-item
        v-for="(slide, i) in slides"
        :key="i"
      >
        <v-sheet
          :color="colors[i]"
          height="100%"
        >
          <v-row
            class="fill-height"
            align="center"
            justify="center"
          >
            <div class="text-h2">
              #{{ slide }} hot live
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>

    <!-- 하단 all live 카드 --> 
    <div style="font-family: 'InfinitySans-RegularA1';">
      <div class="main-card">
        <v-card class="mx-auto" width="500">
          <v-container fluid>
            <div>
              <!-- card를 component로 하고 나서 이 글씨가 안보여요 ㅠ --> 
              <span style="font-family: 'PT Serif', serif;font-size:20px; margin-bottom:20px"><b>All Live</b></span>
            </div>
            <v-row dense>
              <MyLiveListCard v-for="(live, idx) in lives" :key="idx" :live="live"/>
            </v-row>
          </v-container>
        </v-card>
      </div>
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
      colors: [
          'indigo',
          'warning',
          'pink darken-2',
          'red lighten-1',
        ],
        slides: [
          '1',
          '2',
          '3',
          '4',
        ],
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
    getAllLiveList: function () {
      rest.axios({
        method: 'get',
        url: '/dabid/live/all',
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
      this.getAllLiveList()
    } else {
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style>

</style>