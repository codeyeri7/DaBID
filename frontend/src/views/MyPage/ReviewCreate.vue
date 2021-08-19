<template>
  <div class="container" id="kor-font">
    <v-card style="padding:10px" tile elevation="1">
      <h4 class="gold-color">거래 정보</h4>
      <v-text>라이브 제목: {{ live.Title }}</v-text><br>
      <v-text>낙찰가</v-text><br>
      <v-text>상품 품목: {{ live.prdName }}</v-text> <br>
      <v-text>상품 품목: {{ live.user.userName }}</v-text> 
    </v-card>

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
      // 받아온 라이브 번호 
      prdId: null,
      // 받아온 라이브 정보 
      live: null,
      // 판매자 
      seller: null,
      // 점수 합계 
      userscore: null,

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
      console.log(this.prdId, '!!!!!!!!!!!!!')
      rest.axios({
        method: 'get',
        url: `/dabid/live/${this.prdId}`,
        headers: this.setToken()
      })
        .then((res) => {
          // 거래한 라이브 정보 
          this.live = res.data
          console.log('가져온 라이브 데이터', this.live)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getScore() {
      if (this.clicked1 == true) {
        this.userscore += 1;
      }
      if (this.clicked3 == true) {
        this.userscore += 1;
      }
      if (this.clicked5 == true) {
        this.userscore += 1;
      }
      if (this.clicked2 == true) {
        this.userscore -= 5;
      }
      if (this.clicked4 == true) {
        this.userscore -= 5;
      }
      if (this.clicked6 == true) {
        this.userscore -= 5;
      }
      console.log(this.userscore)
    },
    createReview: function () {
      this.getScore()
      const config = this.setToken()
      const review = {
        userId: this.seller.userId,
        userscore: this.userscore,
        prdId: this.prdId,
        content: this.content,
      };
      rest
        .axios({
          method: "post",
          url: "/dabid/users/writeReview/",
          data: review,
          headers: config
        })
        .then((res) => {
          console.log(res);
          this.$router.push({ name: "ReviewList", params: { userId: this.seller.userId }});
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
    created() {
      if (localStorage.getItem('jwt')) {
        // 바로 정보 가져오기
        this.prdId = this.$route.params.prdId
        this.seller = this.$route.params.seller
        console.log(this.prdId, '받아온 라이브 번호', this.seller)
        this.getLive();
      } else {
        console.log('오류')
      }
    }
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
  margin-bottom: 60px;
}
.submit-buttom {
  display:flex; 
  justify-content:center;
  font-size: 1.1rem;
  background-color: #292938;
  margin: auto;
}
v-text {
  color: #dfb772;
}
</style>