<template>
    <div class="container" style="padding: 0px">
      <v-card
        class="mx-auto elevation-3"
        style="max-width: 80%; margin-top: 50px;background-color:#3c3f44"
      >
        <v-row justify="space-between">
          <v-col>
            <v-card-title>
              <div>
                <img src="@/assets/profileImg.png" alt="profile image" width="50" />
                <span class="ml-3" id="userName"><b>{{ Person.userName }}</b></span>
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="600px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      color="white"
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <v-card>
                    <v-container>
                      <h3 style="font-size:15px; font-weight:bold" id="kor-font">닉네임 변경</h3>
                      <div>
                        <v-text-field
                          v-model="userName"
                          required
                          id="kor-font"
                        ></v-text-field>
                      </div>
                    </v-container>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="#E53935"
                        text
                        @click="dialog = false"
                      >
                        Close
                      </v-btn>
                      <v-btn
                        color="primary"
                        text
                        @click="nameUpdate()"
                      >
                        Save
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </v-card-title>
          </v-col>
        </v-row>

        <div id="barEmoji">
          <v-progress-linear
            v-model="Person.userScore"
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
      </v-card>
    </div>
</template>

<script>
import rest from "../js/httpCommon.js";
export default {
  name: "MyProfile",
  data: function () {
    return {
      dialog: false,
      reviews: [],
      userName: null,
      userId: null,
    };
  },
  props: {
    Person: Object
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${jwtToken}`,
      };
      return config;
    },
    checkReviews: function () {
      //남이 나에게 써준 리뷰
      rest
        .axios({
          method: "get",
          url: `/dabid/users/checkReview/${this.person.userId}`,
        })
        .then((res) => {
          this.reviews = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    writeReviews: function () {
      this.$router.push({ name: "ReviewCreate" });
    },
    nameUpdate: function () {
      const config = this.setToken()
      rest.
        axios({
          method: 'patch',
          url: `/dabid/users/`,
          data: {
            userName: this.userName,
          },
          headers: config
        })
        .then(() => {
          localStorage.setItem('userName', this.userName)
          this.dialog = false
          this.$router.go()
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.userId = localStorage.getItem('userId')
      this.checkReviews
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