<template>
  <div class="container">
    <!-- top2 carousel -->
    <v-row style="width: 90%; margin: auto; margin-bottom: 7%">
      <v-card
        class="mx-auto"
        color="#455A64"
        dark
        width="50%"
        v-for="(hot_live, idx) in hot_lives"
        :key="idx"
        :hot_live="hot_live"
      >
        <v-card-title>
          <v-list-item-avatar color="white">
            <v-img
              class="elevation-6"
              alt="profile image"
              src="https://avataaars.io/?avatarStyle=Transparent&topType=LongHairStraight&accessoriesType=Kurt&hairColor=Red&facialHairType=Blank&clotheType=ShirtVNeck&clotheColor=Blue03&eyeType=Hearts&eyebrowType=RaisedExcitedNatural&mouthType=Default&skinColor=Light"
            ></v-img>
          </v-list-item-avatar>
          <div>
            <v-icon class="mr-1"> mdi-heart </v-icon>
            <span class="subheading mr-2">28</span>
          </div>
          <h5>{{ hot_live.user.userName }}</h5>
        </v-card-title>

        <v-card-text id="kor-font">
          {{ hot_live.liveTitle | truncate(9, "...") }} <br />
          <span> <b>시작가</b> </span> | {{ hot_live.prdPriceStart }}
        </v-card-text>
      </v-card>
    </v-row>

    <!-- 검색 카테고리 창 -->
    <v-expansion-panels id="panel">
      <v-expansion-panel>
        <v-expansion-panel-header> Search </v-expansion-panel-header>

<<<<<<< HEAD
      <v-expansion-panel-content>
        <v-col>
          <v-autocomplete
            v-model="values1"
            :items="items1"
            dense
            chips
            small-chips
            label="카테고리"
            multiple
          ></v-autocomplete>
          <v-autocomplete
            v-model="values2"
            :items="items2"
            dense
            chips
            small-chips
            label="방송 상태"
            multiple
          ></v-autocomplete>
         <v-text-field
          label="검색어"
          v-model="keyword"
        ></v-text-field>
        <v-btn @click="search()">검색</v-btn>
        </v-col>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
=======
        <v-expansion-panel-content>
          <v-col>
            <v-autocomplete
              v-model="values1"
              :items="items1"
              dense
              chips
              small-chips
              label="카테고리"
              multiple
            ></v-autocomplete>
            <v-autocomplete
              v-model="values2"
              :items="items2"
              dense
              chips
              small-chips
              label="방송 상태"
              multiple
            ></v-autocomplete>
            <v-text-field label="검색어"></v-text-field>
            <v-btn @click="search()">검색</v-btn>
          </v-col>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
>>>>>>> d963576a95ffa4916877653f1207fab4770aa7a1

    <!-- 하단 all live 카드 -->
    <div style="font-family: 'InfinitySans-RegularA1'">
      <div class="main-card">
        <v-card class="mx-auto" width="500">
          <v-container fluid>
            <div>
              <span
                style="
                  font-family: 'PT Serif', serif;
                  font-size: 20px;
                  margin-bottom: 20px;
                "
                ><b>All Live</b></span
              >
            </div>
            <v-row dense>
<<<<<<< HEAD
              <MyLiveListCard v-for="(live, idx) in lives" :key="idx" :live="live"/>
              <div v-if="this.lives.length == 0">검색 결과가 없습니다.</div>
=======
              <MyLiveListCard
                v-for="(live, idx) in lives"
                :key="idx"
                :live="live"
              />
>>>>>>> d963576a95ffa4916877653f1207fab4770aa7a1
            </v-row>
          </v-container>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import MyLiveListCard from "../../components/MyLiveListCard.vue";
import rest from "../../js/httpCommon.js";

export default {
  name: "MyLiveList",
  components: { MyLiveListCard },
  data: function () {
    return {
      hot_lives: [],
      lives: [],

      //검색 관련
<<<<<<< HEAD
      keyword: '',
      values1: '',
      values2: '',
      items1: ['의류', '가방', '신발', '악세사리'],
      items2: ['방송종료', '방송중', '방송예정']
    }
=======
      values1: "",
      values2: "",
      items1: ["의류", "가방", "신발", "악세사리"],
      items2: ["방송 종료", "방송 진행 중", "방송 예정"],
    };
>>>>>>> d963576a95ffa4916877653f1207fab4770aa7a1
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${jwtToken}`,
      };
      return config;
    },
    getAllLiveList: function () {
<<<<<<< HEAD
      rest.axios({
        method: 'get',
        url: '/dabid/live',
        headers: this.setToken()
      })
=======
      rest
        .axios({
          method: "get",
          url: "/dabid/live/all",
          headers: this.setToken(),
        })
>>>>>>> d963576a95ffa4916877653f1207fab4770aa7a1
        .then((res) => {
          this.lives = res.data;
          console.log("전체 라이브", this.lives);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getHotLives: function () {
<<<<<<< HEAD
      rest.axios({
        method: 'get',
        url: '/dabid/live/best',
      })
      .then((res) => {
        console.log('받아온 인기 방송', res.data)
        this.hot_lives = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    },
    search() {
      console.log(this.values1)
      rest.axios({
        method: 'get',
        url: '/dabid/live',
        params: {
          categories: this.values1 + '',
          liveStatuses: this.values2 + '',
          keyword: this.keyword,
        },
      })
      .then((res) => {
        this.lives = res.data
        console.log('검색결과', this.lives)
      })
      .catch((err) => {
        console.log(err)
      })
=======
      rest
        .axios({
          method: "get",
          url: "/dabid/wish/best",
        })
        .then((res) => {
          console.log("받아온 인기 방송", res.data);
          this.hot_lives = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    search() {
      console.log(this.values);
      rest
        .axios({
          method: "get",
          url: "/dabid/live",
        })
        .then((res) => {
          this.lives = res.data;
          console.log("검색결과", this.lives);
        })
        .catch((err) => {
          console.log(err);
        });
>>>>>>> d963576a95ffa4916877653f1207fab4770aa7a1
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getAllLiveList();
      this.getHotLives();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style scoped>
.mx-auto {
  border-radius: 30px;
}
</style>