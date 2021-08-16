<template>
  <div>
    <h3 id="eng-font" style="margin-left: 10px">Top 2</h3>
    <!-- top2 carousel -->
    <div class="mx-auto">
      <v-container fluid>
        <v-row dense>
          <v-col
            v-for="hot_live in hot_lives"
            :key="hot_live.live.liveTitle"
            :cols="6"
          >
            <v-card color="secondary" tile :elevation="0" light height="190">
              <v-card-title>
                <v-list-item-avatar>
                  <v-img
                    class="elevation-6"
                    alt="profile image"
                    src="@/assets/best-seller.png"
                  ></v-img>
                </v-list-item-avatar>
                <div>
                  <v-icon class="mr-1" style="color: red"> mdi-heart </v-icon>
                  <span class="subheading mr-2">{{ hot_live.hearts }}</span>
                </div>
                <h5>{{ hot_live.user.userName }}</h5>
              </v-card-title>

              <v-card-text id="kor-font">
                <p>
                  <b>{{ hot_live.live.liveTitle | truncate(9, "..") }}</b>
                </p>
                <p>₩ {{ hot_live.live.prdPriceStart | comma }}</p>
                {{ hot_live.live.liveDate.slice(0, 9) }}
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>

    <!-- 검색 카테고리 창 -->
    <v-expansion-panels id="panel">
      <v-expansion-panel>
        <v-expansion-panel-header id="eng-font">
          Search
        </v-expansion-panel-header>

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
            <v-text-field label="검색어" v-model="keyword"></v-text-field>
            <div class="search-button" id="kor-font">
              <v-btn light color="secondary" @click="reset" style="color: black"
                >초기화</v-btn
              >
              <v-btn dark color="primary" @click="search">검색</v-btn>
            </div>
          </v-col>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>

    <!-- 하단 all live 카드 -->
    <div id="kor-font">
      <div class="main-card">
        <div class="mx-auto">
          <v-container fluid>
            <div>
              <span id="eng-font" style="font-size: 20px; margin-bottom: 20px">
                <b>All Live</b>
              </span>
            </div>
            <v-row dense>
              <MyLiveListCard
                v-for="(live, idx) in lives"
                :key="idx"
                :live="live"
              />
              <div v-if="this.lives.length == 0">검색 결과가 없습니다.</div>
            </v-row>
          </v-container>
        </div>
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
      keyword: "",
      values1: "",
      values2: "",
      items1: ["의류", "가방", "신발", "악세사리"],
      items2: ["방송종료", "방송중", "방송예정"],
    };
  },
  filters: {
    comma: function (value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
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
      rest
        .axios({
          method: "get",
          url: "/dabid/live",
          headers: this.setToken(),
        })
        .then((res) => {
          this.lives = res.data;
          console.log("전체 라이브", this.lives);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getHotLives: function () {
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
      console.log(this.values1);
      rest
        .axios({
          method: "get",
          url: "/dabid/live",
          params: {
            categories: this.values1 + "",
            liveStatuses: this.values2 + "",
            keyword: this.keyword,
          },
        })
        .then((res) => {
          this.lives = res.data;
          console.log("검색결과", this.lives);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    reset() {
      (this.values1 = ""), (this.values2 = ""), (this.keyword = "");
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
#panel {
  width: 95%;
  margin: auto;
}
.search-button {
  display: flex;
  justify-content: space-around;
}
p {
  margin: 0;
}
h5 {
  margin: 0;
}
h3 {
  margin-bottom: 2px;
}
</style>