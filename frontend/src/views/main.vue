<template>
  <div style="font-family: 'InfinitySans-RegularA1'">
    <!-- carousel -->
    <div class="main-carousel">
      <v-carousel :show-arrows="false" height="230">
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
        ></v-carousel-item>
      </v-carousel>
    </div>

    <!-- card -->
    <div class="main-card">
      <v-card class="mx-auto" max-width="500">
        <v-container fluid>
          <div>
            <span><b>방송중인 상품</b></span>
            <span align="right" style="padding-left: 200px">더보기</span>
          </div>
          <v-row dense>
            <v-col v-for="card in cards" :key="card.title" :cols="card.flex">
              <v-card class="section1">
                <!-- Image -->
                <v-img
                  :src="card.src"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding: 40px"
                >
                  <!-- image 안 title -->
                  <v-card-title v-text="card.title"></v-card-title>
                </v-img>

                <!-- 카드 하단-->
                <v-card-actions style="height: 70px">
                  <!-- title -->
                  <v-card-text v-text="card.livetitle"> </v-card-text>
                  <!-- 정보 -->
                  <v-card-text v-text="card.text"> </v-card-text>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
        <div>
          <button class="btn btn-large btn-primary me-1" @click="goLive1">
            방송 켜기
          </button>
          <button class="btn btn-large btn-primary ms-1" @click="goLive2">
            방송 참여하기
          </button>
        </div>
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
                  :src="card.src"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding-left: 20px"
                >
                  <!-- image 안 title -->
                  <v-card-title v-text="card.title"></v-card-title>
                </v-img>

                <!-- 카드 하단-->
                <v-card-actions style="height: 70px">
                  <!-- title -->
                  <v-card-text v-text="card.livetitle"> </v-card-text>
                  <!-- 정보 -->
                  <v-card-text v-text="card.text"> </v-card-text>
                </v-card-actions>
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
                  :src="card.src"
                  class="white--text align-center"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px"
                  style="padding-left: 25px"
                >
                  <!-- image 안 title -->
                  <v-card-title v-text="card.title"></v-card-title>
                </v-img>

                <!-- 카드 하단-->
                <v-card-actions style="height: 70px">
                  <!-- title -->
                  <v-card-text v-text="card.livetitle"> </v-card-text>
                  <!-- 정보 -->
                  <v-card-text v-text="card.text"> </v-card-text>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
    </div>
    <!-- 상품 등록 버튼 -->
    <div class="fixedbutton">
      <RouterLink :to="{ name: 'LiveInfo' }">
        <v-btn class="mx-2" fab dark color="secondary">
          <v-icon dark>mdi-plus</v-icon>
        </v-btn>
      </RouterLink>
    </div>
  </div>
</template>

<script>
//import axios from "axios";
import rest from "../js/httpCommon.js";

export default {
  name: "Main",
  data() {
    return {
      cards: [
        {
          title: "방송중",
          livetitle: "제목",
          text: "30만원",
          src: "",
          flex: 6,
        },
        {
          title: "방송중",
          livetitle: "제목",
          text: "30만원",
          src: "",
          flex: 6,
        },
      ],
      cards2: [
        {
          title: "7/29목 7pm",
          livetitle: "제목",
          text: "30만원",
          src: "",
          flex: 6,
        },
        {
          title: "7/30금 2pm",
          livetitle: "제목",
          text: "30만원",
          src: "",
          flex: 6,
        },
      ],
      cards3: [
        {
          title: "방송 종료",
          livetitle: "제목",
          text: "30만원",
          src: "",
          flex: 6,
        },
        {
          title: "방송 종료",
          livetitle: "제목",
          text: "30만원",
          src: "",
          flex: 6,
        },
      ],
      sessionData: {
        liveTitle: '',
        token: '',
        userName:'',
        userId: ''
      },

      // carousel image
      items: [
        {
          // src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
        },
        {
          // src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg",
        },
        {
          // src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
        },
      ],
    };
  },
  methods: {
    // live 열기
    goLive1: function () {
      rest
        .axios({
          method: "post",
          url: "/dabid/session/3/test123",
        })
        .then((res) => {
          // console.log("켜짐");
          // console.log(res.data)
          console.log('Publisher입니다.')
          this.sessionData.liveTitle = res.data.liveTitle
          this.sessionData.token = res.data.token
          this.sessionData.userName = res.data.userName
          this.sessionData.userId = res.data.userId
          console.log('session'+  this.sessionData);
          this.$router.push({ name: "Session" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goLive2: function () {
      rest
      .axios({
        method: "post",
        url: "/dabid/session/3/test123",
      })
        .then((res) => {
          console.log(res);
          console.log('Subscriber입니다.')
          this.$router.push({ name: "Session" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // live 정보
    getLive: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/live/",
        })
        .then((res)=> {
          // card에 어떻게 저장되는지 모르겠음 console 찍어봐야 함
          this.card = res.data
          console.log(res.data)
        })
        .catch((err) => {
          console.log(err)
          // console.log('nono')
        })
    }
  },
  // 페이지 열리자마자 live 정보들 가져오기
  created: function() {
    // this.getLive()
  },
};
</script>

<style scope>
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
  bottom: 20px;
  padding-left: 250px;
}
</style>
