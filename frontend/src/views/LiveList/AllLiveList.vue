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
        v-for="(hot_live, i) in hot_lives"
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
              hot_live.liveTitle
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>

    <!-- 검색 카테고리 창 --> 
    <v-expansion-panels>
     <v-expansion-panel>
      <v-expansion-panel-header>
        Search
      </v-expansion-panel-header>

      <v-expansion-panel-content>
        <v-col cols="12">
          <v-autocomplete
            v-model="values"
            :items="items"
            dense
            chips
            small-chips
            label="카테고리"
            multiple
          ></v-autocomplete>
        </v-col>
         <v-text-field
          label="검색어"
        ></v-text-field>
        <v-btn>검색</v-btn>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>


    <!-- 하단 all live 카드 --> 
    <div style="font-family: 'InfinitySans-RegularA1';">
      <div class="main-card">
        <v-card class="mx-auto" width="500">
          <v-container fluid>
            <div>
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
      hot_lives: [],
      lives: [],

      //검색 관련
      values: '',
      items: ['의류', '가방', '신발', '악세사리']

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
          console.log('전체 라이브', this.lives)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getHotLives: function () {
      rest.axios({
        method: 'get',
        url: '/dabid/live',
      })
      .then((res) => {
        console.log('받아온 인기 방송', res.data)
        this.hot_lives = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    }
  },
  created: function () {
    if (localStorage.getItem('jwt')) {
      this.getAllLiveList()
      this.getHotLives()
    } else {
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style>

</style>