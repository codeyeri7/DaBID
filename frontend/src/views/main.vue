<template>
  <div style="font-family: 'InfinitySans-RegularA1';">
    <!-- carousel -->
    <v-carousel
      :continuous="false"
      :show-arrows="false"
      hide-delimiter-background
      delimiter-icon="mdi-minus"
      height="230"
    >
      <v-carousel-item>
        <img src="@/assets/Banner3.png" width="370">
      </v-carousel-item>
      <v-carousel-item>
        <img src="@/assets/Banner2.png" width="370">
      </v-carousel-item>
      <v-carousel-item>
        <img src="@/assets/Banner1.png" width="370">
      </v-carousel-item>
    </v-carousel>

    <button @click="goLive1">goLive1</button>
    <!-- <button @click="goLive2">goLive2</button> -->
    <!-- card -->
    <div class="main-card">
      <v-card class="mx-auto" width="500">
        <v-container fluid>
          <div>
            <span><b>방송중인 상품</b></span>
            <span align="right" style="padding-left: 200px">더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in now_live" :key="card.title" :cols="6">
              <v-card class="section1">
                <!-- Image -->
                <v-img
                  src= "card.prdPhoto"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding: 40px"
                  @click="goLive2"
                >
                  <!-- image 안 title -->
                  <v-card-title class="subtitle-style" style="font-size:18px">방송중</v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-title>{{ card.liveTitle  | truncate(7, '...') }}</v-card-title>
                <v-card-title class="text-subtitle-1">{{ card.prdName | truncate(5, '...') }}</v-card-title>
                <v-card-subtitle class="text-subtitle-4">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
        <div>
        </div>
        <hr />
        <v-container fluid>
          <div>
            <span><b>방송 예정</b></span>
            <span align="right" style="padding-left: 230px">더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in will_live" :key="card.title" :cols="6">
              <v-card class="section2">
                <!-- Image -->
                <v-img
                  src= "card.prdPhoto"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding-left: 20px"
                >
                   <!-- image 안 title -->
                  <v-card-title class="subtitle-style">방송 예정</v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-title>{{ card.liveTitle  | truncate(7, '...') }}</v-card-title>
                <v-card-title class="text-subtitle-1">{{ card.prdName | truncate(5, '...') }}</v-card-title>
                <v-card-subtitle class="text-subtitle-4">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
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
            <v-col v-for="card in end_live" :key="card.title" :cols="6">
              <v-card class="section3">
                <!-- Image -->
                <v-img
                  src= "card.prdPhoto"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding-left: 25px"
                >
                   <!-- image 안 title -->
                  <v-card-title class="subtitle-style">방송 종료</v-card-title>
                </v-img>
                <!-- 카드 하단-->
                <v-card-title>{{ card.liveTitle  | truncate(7, '...') }}</v-card-title>
                <v-card-title class="text-subtitle-1">{{ card.prdName | truncate(5, '...') }}</v-card-title>
                <v-card-subtitle class="text-subtitle-4">시작가 | {{ card.prdPriceStart | comma }}원</v-card-subtitle>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
    </div>
    <!-- 상품 등록 버튼 -->
    <div class="fixedbutton" style="float: right">
      <RouterLink :to="{ name: 'LiveInfo' }">
        <v-btn 
          x-small 
          class="mr-2" 
          fab dark color="gray"
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
      sessionData: {
        liveTitle: "",
        token: "",
        userName: "",
        userId: "",
        role: "",
      },
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
    goLive1: function () {
      this.$router.push({ name: "session" });
      // rest
      //   .axios({
      //     method: "post",
      //     url: `/dabid/session/10`,
      //     headers: this.setToken()
      //   })
      //   .then((res) => {
      //     console.log("켜짐");
      //     console.log(res.data);
      //     console.log(res.data.role + "입니다.");
      //     this.sessionData.liveTitle = res.data.liveTitle;
      //     this.sessionData.token = res.data.token;
      //     this.sessionData.userName = res.data.userName;
      //     this.sessionData.userId = res.data.userId;
      //     this.sessionData.role = res.data.role;
      //     console.log("token" + res.data.token);
      //     console.log("session" + this.sessionData);
          // this.$router.push({
          //   name: "Session", 
          //   params: {
          //     liveTitle: this.sessionData.liveTitle,
          //     token: this.sessionData.token,
          //     userName: this.sessionData.userName,
          //     userId: this.sessionData.userId,
          //     role: this.sessionData.role,
          //   }
          // });
        // })
        // .catch((err) => {
        //   console.log(err);
        // });
    },
    // goLive2: function () {
    //   rest
    //   .axios({
    //     method: "post",
    //     url: "/dabid/session/2/P1628141880829",
    //   })
    //     .then((res) => {
    //       this.sessionData.liveTitle = res.data.liveTitle;
    //       this.sessionData.token = res.data.token;
    //       this.sessionData.userName = res.data.userName;
    //       this.sessionData.userId = res.data.userId;
    //       this.sessionData.role = res.data.role;
    //       console.log(res);
    //       console.log(res.data.role + "입니다.");
    //       this.$router.push({ 
    //         name: "Session",
    //         params: {
    //           liveTitle: this.sessionData.liveTitle, 
    //           token: this.sessionData.token, 
    //           userName: this.sessionData.userName, 
    //           userId: this.sessionData.userId,
    //           role: this.sessionData.role
    //         }
    //       });
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },

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
@font-face {
  font-family: "InfinitySans-RegularA1";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
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
.subtitle-style {
  height: 20px;
  font-size: 3pt;
  font-family: 'IBMPlexSansKR-Regular';
}
.custom-selector {
  font-size: 3em;
}
</style>