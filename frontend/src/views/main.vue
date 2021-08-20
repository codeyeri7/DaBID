<template>
  <div class="main-back">
    <!-- carousel-->
    <v-carousel
      :continuous="false"
      :show-arrows="false"
      hide-delimiter-background
      delimiter-icon="mdi-minus"
      height="230"
    >
      <v-carousel-item>
        <img src="@/assets/Banner3.png" width="100%" height="100%">
      </v-carousel-item>
      <v-carousel-item>
        <img src="@/assets/Banner2.png" width="100%" height="100%">
      </v-carousel-item>
      <v-carousel-item>
        <img src="@/assets/Banner1.png" width="100%" height="100%">
      </v-carousel-item>
    </v-carousel>

    <!-- card -->
    <div class="main-card">
      <div class="mx-auto">
        <v-container fluid>
          <div class="classify">
            <span><b>방송중인 상품</b></span>
            <span @click="goSearchLive('방송중')">더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in now_lives" :key="card.title" :cols="6">
              <v-card height="280" class="section1" @click="goLive(card.prdId)" tile :elevation="0">
                <!-- Image -->
                <v-img
                  :src= "card.prdPhoto"
                  class="white--text align-center"
                  height="180px"
                  style="padding: 40px"
                >
                
                </v-img>
                <!-- 카드 하단-->
                <div class="card-content" id="kor-font">
                  <v-card-title id="card-title">{{ card.liveTitle  | truncate(8, '...') }}</v-card-title><br>
                  <v-card-subtitle class="py-0">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
                  <v-card-subtitle class="pt-0 pb-1">방송일 | {{ card.liveDate.slice(0,10) }}</v-card-subtitle>
                </div>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
        <div>
        </div>
        <v-container fluid>
          <div class="classify">
            <span><b>방송 예정</b></span>
            <span @click="goSearchLive('방송예정')">더보기</span>
          </div>
          <v-row dense>
            <MainCard
              v-for="(live, idx) in will_lives"
              :key="idx"
              :live="live"
            />
          </v-row>
        </v-container>

        <v-container fluid>
          <div class="classify">
            <span><b>방송 종료</b></span>
            <span @click="goSearchLive('방송종료')">더보기</span>
          </div>
          <v-row dense>
            <MainCard
              v-for="(live, idx) in end_lives"
              :key="idx"
              :live="live"
            />
          </v-row>
        </v-container>
      </div>
    </div>
    <!-- 상품 등록 버튼 -->
    <div class="fixedbutton" style="float: right">
      <RouterLink :to="{ name: 'LiveInfo' }">
        <v-btn 
          small
          class="mr-3" 
          fab dark
          color="#dfb772"
        >
          <v-icon dark>mdi-plus</v-icon>
        </v-btn>
      </RouterLink>
    </div>
  </div>
</template>

<script>
import rest from "../js/httpCommon.js"
import MainCard from "../components/MainCard.vue"

export default {
  name: "Main",
  components: {
    MainCard,
  },
  data() {
    return {
      lives: [],
      cards: null,
      now_lives: [],
      will_lives: [],
      end_lives: [],
      wishlist: [],
      dialog: false,
      clicked: false,
    };
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
        Authorization: `Bearer ${jwtToken}`
      }
      return config
    },
    // live 열기
    goLive: function (prdId) {
      this.$router.push({
        name: "session", 
        params: {
          prdId : prdId
        }
      });
    },
    // 더보기로 이동
    goSearchLive(liveStatus) {
      this.$router.push({
        name: "AllLiveList", 
        params: {
          liveStatus : liveStatus
        }
      });
    },
    // live 정보
    getLive: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/live/top2",
          headers: this.setToken(),
        })
        .then((res) => {
          this.cards = res.data;
          for (let one_live of this.cards) {
            if (one_live.liveStatus.liveStatus == 0) {
              this.will_lives.push(one_live)
            }
            else if (one_live.liveStatus.liveStatus == 2) {
              this.end_lives.push(one_live)
            } else {
              this.now_lives.push(one_live)
            }
        }

        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  // 페이지 열리자마자 live 정보들 가져오기
  created: function () {
    this.getLive();
  },
};
</script>

<style>
/* 상품 등록 버튼 고정 */
.fixedbutton {
  position: sticky;
  bottom: 70px;
  padding-left: 250px;
}
.title-style label[for]{
  height: 20px;
  font-size: 4pt;
}
.custom-selector {
  font-size: 3em;
}
/* 카드 밑 바닥  */
.card-content {
  background-color: #3c3f44;
  color: #dfb772
}
.classify {
  display:flex; 
  justify-content:space-between;
  margin-bottom: 0.6rem;
  color: #dfb772;
}
#card-title {
  font-size: 1rem;
  padding-bottom: 0;
  padding-top: 0;
  color:#dfb772;
}
.main-back {
  background-color: #151618;
  height: 100%;
  margin-bottom: 50px;
}
.live-time {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 160px;
  height: 100%;
  z-index: 1;
}
.font {
  font-size: 1.25rem;
}
</style>