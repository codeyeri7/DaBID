<template>
  <div>
    <img src="" alt="" />
    <v-text>live.prdName</v-text>
    <v-text>낙찰가</v-text>
    <v-divider></v-divider>
    <v-text>Q1. 라이브는 문제없이 진행됐나요?</v-text>
    <v-btn
      icon
      v-bind:class="{ gray: !clicked1, red: clicked1 }"
      v-on:click="clicked1 = !clicked1"
      :disabled="clicked2 === true"
    >
      <v-icon>mdi-thumb-up</v-icon>
    </v-btn>
    <v-btn
      icon
      v-bind:class="{ gray: !clicked2, red: clicked2 }"
      v-on:click="clicked2 = !clicked2"
      :disabled="clicked1 === true"
    >
      <v-icon>mdi-thumb-down</v-icon>
    </v-btn>
    <v-divider></v-divider>
    <v-text>Q2. 물건을 잘 받으셨나요?</v-text>
    <v-btn
      icon
      v-bind:class="{ gray: !clicked3, red: clicked3 }"
      v-on:click="clicked3 = !clicked3"
      :disabled="clicked4 === true"
    >
      <v-icon>mdi-thumb-up</v-icon>
    </v-btn>
    <v-btn
      icon
      v-bind:class="{ gray: !clicked4, red: clicked4 }"
      v-on:click="clicked4 = !clicked4"
      :disabled="clicked3 === true"
    >
      <v-icon>mdi-thumb-down</v-icon>
    </v-btn>
    <v-divider></v-divider>
    <v-text>Q3. 안내된 품질과 동일한가요?</v-text>
    <v-btn
      icon
      v-bind:class="{ gray: !clicked5, red: clicked5 }"
      v-on:click="clicked5 = !clicked5"
      :disabled="clicked6 === true"
    >
      <v-icon>mdi-thumb-up</v-icon>
    </v-btn>
    <v-btn
      icon
      v-bind:class="{ gray: !clicked6, red: clicked6 }"
      v-on:click="clicked6 = !clicked6"
      :disabled="clicked5 === true"
    >
      <v-icon>mdi-thumb-down</v-icon>
    </v-btn>
    <v-divider></v-divider>
    <v-textarea
      v-model="content"
      label="Message"
      counter
      maxlength="120"
      full-width
      single-line
    ></v-textarea>
    <v-btn
      @click="createReview()"
      x-small
      color="white"
      style="
        margin-left: 120px;
        margin-top: 20px;
        margin-bottom: 10px;
        padding: 17px;
        font-size: 17px;
        color: black;
        font-family: 'IBMPlexSansKR-Regular';
      "
      >등록</v-btn
    >
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js";

export default {
  data() {
    return {
      //prdId : this.prdId
      prdId: 9,
      content: "",
      userscore: 4,
      clicked1: false,
      clicked2: false,
      clicked3: false,
      clicked4: false,
      clicked5: false,
      clicked6: false,
    };
  },
  methods: {
    createReview() {
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

<style>
.gray {
  background-color: gray;
}
.red {
  background-color: red;
}
</style>