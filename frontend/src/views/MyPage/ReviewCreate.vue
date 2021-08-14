<template>
  <div class="container" id="kor-font">
    <h4>거래 정보</h4>
    <!-- <img src="" alt="" />
    <v-text>live.prdName</v-text>
    <v-text>낙찰가</v-text>
    <v-text>{{ this.live }}</v-text> -->
    <h3>리뷰 작성</h3>
    <v-divider></v-divider>
    <h5>라이브 품질</h5>
    <v-text>Q1. 라이브는 문제없이 진행됐나요?</v-text>
    <v-btn
      icon
      v-on:click="clicked1 = !clicked1"
      :disabled="clicked2 === true"
    >
      <v-icon v-if="clicked1" style="color:red">mdi-thumb-up</v-icon>
      <v-icon v-if="!clicked1">mdi-thumb-up</v-icon>
    </v-btn>
    <v-btn
      icon
      v-on:click="clicked2 = !clicked2"
      :disabled="clicked1 === true"
    >
      <v-icon v-if="clicked2" style="color:red">mdi-thumb-down</v-icon>
      <v-icon v-if="!clicked2">mdi-thumb-down</v-icon>
    </v-btn>
    <v-divider></v-divider>
    <h5>거래 과정</h5>
    <v-text>Q2. 물건을 잘 수령하셨나요?</v-text>
    <v-btn
      icon
      v-on:click="clicked3 = !clicked3"
      :disabled="clicked4 === true"
    >
      <v-icon v-if="clicked3" style="color:red">mdi-thumb-up</v-icon>
      <v-icon v-if="!clicked3">mdi-thumb-up</v-icon>
    </v-btn>
    <v-btn
      icon
      v-on:click="clicked4 = !clicked4"
      :disabled="clicked3 === true"
    >
      <v-icon v-if="clicked4" style="color:red">mdi-thumb-down</v-icon>
      <v-icon v-if="!clicked4">mdi-thumb-down</v-icon>

    </v-btn>
    <v-divider></v-divider>
    <h5>제품 신뢰도</h5>
    <v-text>Q3. 안내된 품질과 동일한가요?</v-text>
    <v-btn
      icon
      v-on:click="clicked5 = !clicked5"
      :disabled="clicked6 === true"
    >
      <v-icon v-if="clicked5" style="color:red">mdi-thumb-up</v-icon>
      <v-icon v-if="!clicked5">mdi-thumb-up</v-icon>

    </v-btn>
    <v-btn
      icon
      v-on:click="clicked6 = !clicked6"
      :disabled="clicked5 === true"
    >
      <v-icon v-if="clicked6" style="color:red">mdi-thumb-down</v-icon>
      <v-icon v-if="!clicked6">mdi-thumb-down</v-icon>
    </v-btn>
    <v-divider></v-divider>
    <v-textarea
      v-model="content"
      label="한 줄 리뷰"
      counter
      maxlength="120"
      full-width
      single-line
    ></v-textarea>
    <v-btn
      @click="createReview()"
      dark
      color="primary"
      class="submit-buttom"
      id="kor-font"
      >등록</v-btn
    >
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js";

export default {
  data() {
    return {
      live: [],
      content: "",
      clicked1: false,
      clicked2: false,
      clicked3: false,
      clicked4: false,
      clicked5: false,
      clicked6: false,
    };
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${jwtToken}`,
      };
      return config;
    },
    getLive: function () {
      const prdId = this.$route.params.prdId
      rest.axios({
        method: 'get',
        url: `/dabid/live/${prdId}`,
        headers: this.setToken()
      })
        .then((res) => {
          this.live = res.data
        })
        .catch((err) => {
          console.log(err)
        })
    },
    createReview: function () {
      const config = this.setToken()
      const review = {
        //userId: localStorage.getItem("userId"),
        userId: "P1628213340945",
        userscore: this.userscore,
        prdId: this.prdId,
        content: this.content,
      };
      console.log(this.userscore);
      if (this.userscore > 0 || this.userscore < 0) {
        console.log("등록시작"),
        console.log(this.review),
        rest
          .axios({
            method: "post",
            //url: `/dabid/users/${this.prdId}`,
            url: "/dabid/users/writeReview/",
            data: review,
            headers: config
          })
          .then((res) => {
            console.log(res);
            // this.$router.push({ name: "ReviewList" });
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    //  if로 점수 추가할 때 참고하기...
    mounted() {
      if (
        this.clicked1 == true ||
        this.clicked3 == true ||
        this.clicked5 == true
      ) {
        this.userscore += 1;
        console.log(this.userscore)
      }
      if (
        this.clicked2 == true ||
        this.clicked4 == true ||
        this.clicked6 == true
      ) {
        this.userscore -= 5;
      }
      //   if (localStorage.getItem('jwt')) {
      //     // 바로 정보 가져오기
      //     this.live = this.$route.params.live
      //     // console.log(this.live)
      //   } else {
      //     console.log('오류')
      //   }
      //   this.calcDate()
    },
  },
};
</script>

<style scoped>
.gray {
  background-color: gray;
}
.red {
  background-color: red;
}
.container {
  width: 90%;
}
.submit-buttom {
  display:flex; 
  justify-content:center;
  font-size: 1.1rem;
  background-color: #292938;
  margin: auto;
}
</style>