<template>
  <div>
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
            <span>더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in now_live" :key="card.title" :cols="6">
              <v-card height="300" class="section1" @click="goLive(card.prdId)" tile :elevation="0">
                <!-- Image -->
                <v-img
                  src= "card.prdPhoto"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="180px"
                  style="padding: 40px"
                >
                  <!-- image 안 title -->
                  <!-- <v-card-title class="subtitle-style" style="font-size:18px">방송중</v-card-title> -->
                </v-img>
                <!-- 카드 하단-->
                <div class="card-content" id="kor-font">
                  <v-card-title id="card-title">{{ card.liveTitle  | truncate(8, '...') }}</v-card-title><br>
                  <v-card-subtitle class="py-0">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
                  <v-card-subtitle class="pt-0 pb-1">방송일 | {{ card.liveDate | truncate(10, '.') }}</v-card-subtitle>
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
            <span>더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in will_live" :key="card.title" :cols="6">
              <v-card height="300" class="section2" tile :elevation="0">
                <!-- Image -->
                <v-img
                  src= "card.prdPhoto"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="180px"
                  style="padding-left: 20px"
                >
                   <!-- image 안 title -->
                  <!-- <v-card-title class="subtitle-style">방송 예정</v-card-title> -->
                </v-img>
                <!-- 카드 하단-->
                <div class="card-content" id="kor-font">
                   <v-card-title id="card-title">{{ card.liveTitle  | truncate(8, '...') }}</v-card-title><br>
                  <v-card-subtitle class="py-0">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
                  <v-card-subtitle class="pt-0 pb-1">방송일 | {{ card.liveDate | truncate(10, '.') }}</v-card-subtitle>
                </div>
              </v-card>
            </v-col>
          </v-row>
        </v-container>

        <v-container fluid>
          <div class="classify">
            <span><b>방송 종료</b></span>
            <span>더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in end_live" :key="card.title" :cols="6">
              <v-card height="300" class="section3" tile :elevation="0">
                <!-- Image -->
                <v-img
                  src= "card.prdPhoto"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="180px"
                  style="padding-left: 25px"
                >
                   <!-- image 안 title -->
                  <!-- <v-card-title class="subtitle-style">방송 종료</v-card-title> -->
                </v-img>
                <!-- 카드 하단-->
                <div class="card-content" id="kor-font">
                  <v-card-title id="card-title">{{ card.liveTitle  | truncate(8, '...') }}</v-card-title><br>
                  <v-card-subtitle class="py-0">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
                  <v-card-subtitle class="pt-0 pb-1">방송일 | {{ card.liveDate | truncate(10, '.') }}</v-card-subtitle>
                </div>
              </v-card>
            </v-col>
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
          color="#292938"
        >
          <v-icon dark>mdi-plus</v-icon>
        </v-btn>
      </RouterLink>
    </div>
  </div>
</template>

<script>
import rest from "../js/httpCommon.js"

export default {
  name: "Main",
  
  data() {
    return {
      cards: null,
      now_live: null,
      will_live: null,
      end_live: null,
      // sessionData: {
      //   liveTitle: "",
      //   token: "",
      //   userName: "",
      //   userId: "",
      //   role: "",
      // },
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
          // liveTitle: this.sessionData.liveTitle,
          // token: this.sessionData.token,
          // userName: this.sessionData.userName,
          // userId: this.sessionData.userId,
          // role: this.sessionData.role,
        }
      });
    },
    // live 정보
    getLive: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/live/top2",
        })
        .then((res) => {
          this.cards = res.data;
          this.now_live = this.cards.slice(0, 2)
          this.will_live = this.cards.slice(2, 4)
          this.end_live = this.cards.slice(4, 6)
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
  bottom: 60px;
  padding-left: 250px;
}
.title-style label[for]{
  height: 20px;
  font-size: 4pt;
}
/* .subtitle-style {
  height: 20px;
  font-size: 3pt;
  font-family: 'IBMPlexSansKR-Regular';
} */
.custom-selector {
  font-size: 3em;
}
.card-content {
  background-color: #FDF4F4;
  opacity: 0.8;
  color: black;
}
.classify {
  display:flex; 
  justify-content:space-between;
}
#card-title {
  font-size: 1rem;
  padding-bottom: 0;
  padding-top: 0;
  color:black;
}
</style>