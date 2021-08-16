<template>
  <div>
    <div class="container" style="padding: 0px">
      <v-card
        class="mx-auto elevation-20"
        style="max-width: 80%; margin-top: 50px"
      >
        <v-row justify="space-between">
          <v-col>
            <v-card-title>
              <div>
                <img src="@/assets/profileImg.jpg" alt="profile image" />
                <span id="userName"
                  ><b>{{ userName }}</b></span
                >
              </div>
            </v-card-title>
          </v-col>
        </v-row>
        <div id="barEmoji">
          <!-- v-bind:buffer-value="userScore" -->
          <v-progress-linear
            v-model="userScore"
            color="#D0836E"
            background-color="#F4E3D7"
            height="15"
          ></v-progress-linear>
          <br />
          <div class="emoji">
            <img src="@/assets/sad.png" alt="sad_emoji" />
            <img src="@/assets/neutral.png" alt="neutral" />
            <img src="@/assets/laughing.png" alt="laughing_emojiss" />
          </div>
        </div>
        <div id="review">
          <span>
          </span>
          <button
            id="review-btn"
            class="btn d-flex align-center"
            type="button"
            @click="checkReviews()"
          >
            Check reviews
          </button>

          <!-- <button
            id="review-btn"
            class="btn"
            type="button"
            @click="writeReviews()"
          >
            write your reviews
          </button> -->
        </div>
        <v-divider dark></v-divider>
      </v-card>
    </div>
    <hr id="hrline" />
  </div>
</template>

<script>
import rest from "../js/httpCommon.js";
export default {
  name: "UserProfile",
  props: {
    user: Object,
  },
  data: function () {
    return {
      person: [],
      userName: "",
      userScore: "",
      reviews: [],
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
    getProfile: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/users/me",
          headers: this.setToken(),
        })
        .then((res) => {
          this.person = res.data;
          this.userName = res.data.userName;
          this.userScore = res.data.userScore;
          console.log(res);
          console.log(this.person)
          // this.$router.push({ name: "ReviewList" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //review list로 페이지 전환
    checkReviews: function () {
      //남이 나에게 써준 리뷰
      rest
        .axios({
          method: "get",
          url: `/dabid/users/checkReview/${this.person.userId}`,
        })
        .then((res) => {
          this.reviews = res.data;
          console.log(this.reviews);
          //this.$router.push({ name: "ReviewCreate" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // writeReviews: function () {
    //   this.$router.push({ name: "ReviewCreate" });
    // },
    refreshAll: function () {
      // 새로고침
      this.$router.go();
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getProfile();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style scoped>
.emoji > img {
  width: 2rem;
}
.emoji {
  display: flex;
  justify-content: space-between;
}
#review-btn {
  width: 150px;
  margin: auto;
  padding: 0;
}
#barEmoji {
  width: 85%;
  margin: auto;
  margin-bottom: 1.2rem;
}
#review {
  margin-left: 2rem;
  background-color: rgb(212, 212, 212);
  margin-right: 2rem;
  /* border-radius: 30px; */
}
#hrline {
  height: 1px;
  background: #bbb;
  width: 80%;
  margin: auto;
}
</style>