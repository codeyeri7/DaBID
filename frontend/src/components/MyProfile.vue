<template>
  <div>
      <div class="container" style="padding:0px;">
        <v-card
          class="mx-auto elevation-20"
          style="max-width: 80%; margin-top:50px; "
        >
          <v-row justify="space-between">
            <v-col>
              <v-card-title>
                <div>
                  <img src="@/assets/profileImg.jpg" alt="profile image">
                  <span id="userName"><b>{{ userName }}</b></span>
                </div>
              </v-card-title>
            </v-col>
          </v-row>
          <div id="barEmoji">
             <!-- v-bind:buffer-value="userScore" -->
            <v-progress-linear
                v-model="userScore"
                color="deep-orange darken-1"
                background-color="deep-orange lighten-3"
                height="15"
              ></v-progress-linear>
              <br>
              <div class="emoji">
                <img src="@/assets/sad.png" alt="sad_emoji">
                <img src="@/assets/neutral.png" alt="neutral">
                <img src="@/assets/laughing.png" alt="laughing_emojiss">
              </div>
            </div>
           <div id="review">
              <span>
                <img src="@/assets/positive-vote.png" alt="rating_click_img" style="width:16%">
                </span>
              <button id="review-btn" class="btn" type="button" @click="checkReviews(userName)">Check your reviews</button>
          </div> 
          <v-divider dark></v-divider>
          </v-card>
          </div>
        <hr id="hrline">
        </div>
</template>

<script>
import rest from "../js/httpCommon.js"
export default {
    name: 'MyProfile',
    props: {
      user: Object,
    },
    data: function () {
    return {
      person: [],
      userName: '쓰윤서',
      userScore: 40,
    }
  },
  methods: {
      // review list로 페이지 전환
      checkReviews(userName) {
        rest.axios({
          method: 'get',
          url: '',
          headers: this.setToken()
        })
          .then((res) => {
            this.$router.push({ name: 'ReviewList' })
          })
          .catch((err) => {
            console.log(err)
          })
      }
  },
}
</script>

<style scoped>
.emoji > img {
  width: 2rem
}
.emoji {
  display: flex;
  justify-content: space-between;
}
#review-btn {
  width:150px; 
  margin:auto;
  padding:0
  
}
#userName {
  font-family: 'IBMPlexSansKR-Regular';
}
#barEmoji {
  width: 85%;
  margin:auto;
  margin-bottom:1.2rem;
}
#review {
  margin-left: 2rem;
  background-color:rgb(212, 212, 212);
  margin-right: 2rem;
  border-radius: 30px;
}
#hrline {
  height:1px;
  background:#bbb;
  width: 80%;
  margin: auto;
}
</style>