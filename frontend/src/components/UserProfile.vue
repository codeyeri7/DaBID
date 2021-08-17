<template>
  <div>
    <div class="container" style="padding: 0px">
      <v-card
        class="mx-auto elevation-3"
        style="max-width: 80%; margin-top: 50px;background-color:#3c3f44"
      >
        <v-row justify="space-between">
          <v-col>
            <v-card-title>
              <div>
                <img src="@/assets/profileImg.png" alt="profile image" width="50"/>
                <span id="userName">
                    <b>{{ User.user.userName }}</b>
                </span>
              </div>
            </v-card-title>
          </v-col>
        </v-row>

        <div id="barEmoji">
          <v-progress-linear
            v-model="User.user.userScore"
            color="#D0836E"
            background-color="#F4E3D7"
            height="15"
          ></v-progress-linear>
          <div class="emoji">
            <p>:(</p>
            <p>:|</p>
            <p>:)</p>
            <p>:D</p>
          </div>
        </div>

        <div id="review" @click="checkReviews"> 
          <RouterLink :to="{ name: 'ReviewList', params: { userId: User.user.userId }}">
            <h5 class="pb-3" id="eng-font">{{ reviews.length }} Reviews</h5>
          </RouterLink>
        </div>
      </v-card>
    </div>
    <hr id="hrline" />
  </div>
</template>

<script>
import rest from "../js/httpCommon.js";
export default {
  name: "UserProfile",
  data: function () {
    return {
      person: null,
      reviews: [],
      reviews_num: null,
    };
  },
  props: {
      User: Object
    },  
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${jwtToken}`,
      };
      return config;
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
          this.reviews_num = this.reviews.length
          console.log('리뷰갯수에요',this.reviews_num);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    refreshAll: function () {
      // 새로고침
      this.$router.go();
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.checkReviews;
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
  font-weight: bold;
  color: white;
}
#userName {
  font-family: "IBMPlexSansKR-Regular";
  color:white
}
#barEmoji {
  width: 85%;
  margin: auto;
  margin-bottom: 1.2rem;
}
#review {
  margin-left: 10px;
  color:white
}
#hrline {
  height: 1px;
  background: #bbb;
  width: 80%;
  margin: auto;
}
</style>