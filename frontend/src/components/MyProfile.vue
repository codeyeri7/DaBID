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
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="600px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      color="black"
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <v-card>
                    <v-container>
                      <h3 style="font-family: 'Lora', serif;font-size:15px; font-weight:bold">02 Live Info</h3>
                      <div style="font-family: 'IBMPlexSansKR-Regular';">
                        <v-text-field
                          v-model="person.userId"
                          laber="User Id"
                          required
                        ></v-text-field>
                        <v-text-field
                          v-model="person.userName"
                          laber="User Name"
                          required
                        ></v-text-field>
                        <v-text-field
                          v-model="person.userScore"
                          laber="User Score"
                          required
                        ></v-text-field>
                      </div>
                    </v-container>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="nameUpdate()"
                      >
                        Save
                      </v-btn>
                      <v-btn
                        color="primary"
                        text
                        @click="dialog = false"
                      >
                        Close
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </v-card-title>
          </v-col>
        </v-row>
        <div id="barEmoji">
          <!-- v-bind:buffer-value="userScore" -->
          <v-progress-linear
            v-model="userScore"
            color="primary"
            background-color="primary"
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
            <!-- <img
              src="@/assets/positive-vote.png"
              alt="rating_click_img"
              style="width: 16%"
            /> -->
          </span>
          <button
            id="review-btn"
            class="btn"
            type="button"
            @click="checkReviews()"
          >
            Check your reviews
          </button>

          <button
            id="review-btn"
            class="btn"
            type="button"
            @click="writeReviews()"
          >
            write your reviews
          </button>
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
  name: "MyProfile",
  props: {
    user: Object,
  },
  data: function () {
    return {
      dialog: false,
      person: [],
      userName: "",
      userScore: "",
      reviews: [],
      nameChange: []
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
    writeReviews: function () {
      this.$router.push({ name: "ReviewCreate" });
    },
    nameUpdate: function () {
      console.log(this.person)
      const config = this.setToken()
      rest.
        axios({
          method: 'patch',
          url: `/dabid/users/${this.person.userName}`,
          headers: config
        })
        .then((res) => {
          this.nameChange = res.data
          console.log('OK',this.nameChange)
          console.log(res)
          // this.refreshAll();
        })
        .catch((err) => {
          console.log(err)
        })
    },
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
#userName {
  font-family: "IBMPlexSansKR-Regular";
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