<template>
  <div class="body">
    <!-- top2 carousel -->
    <div id="kor-font">
      <div class="main-card">
        <div class="mx-auto">
          <v-container fluid>
            <div>
              <span class="gold-color" id="eng-font" style="font-size: 20px; margin-bottom: 20px">
                <b id="T2">Best</b>
              </span>
            </div>
            <v-row dense>
              <Top2
                v-for="(hot_live, idx) in hot_lives"
                :key="idx"
                :hot_live="hot_live"
              />
            </v-row>
          </v-container>
        </div>
      </div>
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
              <v-btn light color="beige" @click="reset" style="color: black"
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
              <span class="gold-color" id="eng-font" style="font-size: 20px; margin-bottom: 20px">
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
    <v-fab-transition>
      <v-btn color="#dfb772" class="scroll-btn" bottom right fixed fab dark small v-show="btnShow" @click="$vuetify.goTo('#T2')">
        <v-icon color="white">fas fa-angle-double-up</v-icon>
      </v-btn>
    </v-fab-transition>
  </div>
</template>

<script>
import MyLiveListCard from "../../components/MyLiveListCard.vue";
import Top2 from "../../components/Top2.vue"
import rest from "../../js/httpCommon.js";

export default {
  name: "MyLiveList",
  components: { 
    MyLiveListCard,
    Top2,
    },
  data: function () {
    return {
      lives: [],
      hot_lives: [],
    
      //검색 관련
      keyword: "",
      values1: "",
      values2: "",
      items1: ["의류", "가방", "신발", "악세사리"],
      items2: ["방송종료", "방송중", "방송예정"],
      page: 0,
      scrollNo: false
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
          url: "/dabid/live/all",
          headers: this.setToken(),
          params: {
            page: this.page,
          }
        })
        .then((res) => {
          for (let live of res.data.content) {
            this.lives.push(live);
          }
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
    handleScroll() {
      this.btnShow = window.scrollY > 400;
    },
    infiniteScroll(e) {
      let scrollTop = e.target.scrollingElement.scrollTop
      let scrollHeight = e.target.scrollingElement.scrollHeight
      
      if(scrollTop >= scrollHeight - 650) {
        if (this.scrollNo) {
          
          }
        else {
          this.page++;
          this.getAllLiveList();
        }
      }
    }
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getHotLives();
      if (this.$route.params.liveStatus) {
        this.values2 = this.$route.params.liveStatus
        this.scrollNo = true
        this.search()
        }
        else {
          this.getAllLiveList();
        }
    } else {
      this.$router.push({ name: "Login" });
    }
    window.addEventListener('scroll', this.infiniteScroll);
  },
  destroyed () {
    window.removeEventListener('scroll', this.infiniteScroll);
  },
  beforeMount() {
    window.addEventListener("scroll", this.handleScroll)
  },
  beforeDestory() {
    window.removeEventListener("scroll", this.handleScroll)
  }
};

</script>

<style scoped>
.body {
  background-color: #151618;
}
.gold-color {
  color: #dfb772;
}
#panel {
  width: 95%;
  margin: auto;
}
.scroll-btn {
  margin-bottom: 3rem;
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