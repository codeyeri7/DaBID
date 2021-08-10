<template>
<div>
  <!-- top2 carousel --> 
  <v-row>
  <v-card
    class="mx-auto"
    color="#546E7A"
    dark
    width="50%"
    v-for="(hot_live, idx) in hot_lives" :key="idx" :hot_live="hot_live"
  >
    <v-card-title>
      <v-icon
        large
        left
        style="color:#FF7043"
      >
        mdi-star
      </v-icon>
      <span class="text-h6 font-weight-light">Hot LIVE</span>
    </v-card-title>

    <v-card-text class="text-h5 font-weight-bold">
     {{ hot_live.liveTitle }}
    </v-card-text>

    <v-card-actions>
      <v-list-item class="grow">
        <v-list-item-avatar color="grey darken-3">
          <v-img
            class="elevation-6"
            alt="profile image"
            src="https://avataaars.io/"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>{{ hot_live.user.userName }}</v-list-item-title>
        </v-list-item-content>

        <v-row
          align="center"
          justify="end"
        >
          <v-icon class="mr-1">
            mdi-heart
          </v-icon>
          <span class="subheading mr-2">하트 수</span>
    
        </v-row>
      </v-list-item>
    </v-card-actions>
  </v-card>
  </v-row>

    <!-- 검색 카테고리 창 --> 
    <v-expansion-panels>
     <v-expansion-panel>
      <v-expansion-panel-header>
        Search
      </v-expansion-panel-header>

      <v-expansion-panel-content>
        <v-col>
          <v-autocomplete
            v-model="values"
            :items="items"
            dense
            chips
            small-chips
            label="카테고리"
            multiple
          ></v-autocomplete>
         <v-text-field
          label="검색어"
        ></v-text-field>
        <v-btn @click="search()">검색</v-btn>
        </v-col>
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
    },
    search: function () {
      
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