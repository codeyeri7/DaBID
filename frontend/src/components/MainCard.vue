<template>
  <v-dialog v-model="dialog" width="250px">
    <template v-slot:activator="{ on, attrs }">
      <v-col :cols="6">
        <v-card height="280" class="section2" tile :elevation="0" v-bind="attrs" v-on="on">
          <!-- Image -->
          <div>
            <v-img
              :src= "live.prdPhoto"
              class="white--text d-flex justify-center"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="180px"
              style="padding: 40px"
              @click="checkPrdId()"
            >
            <span v-if="live.liveStatus.liveStatus === 0">
              <div class="live-time d-flex flex-column">
                <span class="font">{{ live.liveDate.slice(5,10) }}</span>
                <span class="font">{{ live.liveDate.slice(11,16) }}</span>
              </div>
            </span>
            <span v-else>
              <v-card-title>방송종료</v-card-title>
            </span>
            </v-img>  
          </div>
          <!-- 카드 하단 -->
          <div class="card-content" id="kor-font">
            <v-card-title id="card-title">{{ live.liveTitle  | truncate(8, '...') }}</v-card-title><br>
            <v-card-subtitle class="py-0">시작가 | {{ live.prdPriceStart | comma }}원</v-card-subtitle>
            <v-card-subtitle class="pt-0 pb-1">방송일 | {{ live.liveDate.slice(0,10) }}</v-card-subtitle>
          </div>
        </v-card>
      </v-col>
    </template>
    <v-card>
      <v-card-title class="headline secondary">
        <h4>{{ live.liveTitle }}</h4>
        <span v-if="this.live.liveStatus.liveStatus === 0">
          <span v-if="clicked === false">
            <v-col class="text-right">
              <v-btn icon v-on:click="clicked = !clicked" @click="wish()">
                <v-icon style="color: #CDCDCD">mdi-heart</v-icon>
              </v-btn>
            </v-col>
          </span>
          <span v-else>
            <v-col class="text-right">
              <v-btn icon v-on:click="clicked = !clicked" @click="unwish()">
                <v-icon style="color: #dfb772">mdi-heart</v-icon>
              </v-btn>
            </v-col>
          </span>
        </span>
        <div class="close">
          <img src="@/assets/close.png" @click="dialog = false">
        </div>
      </v-card-title>
      <v-card-text>
        <img :src="live.prdPhoto" width="200px" class="mt-5" />
        <hr />
        <h5 style="margin-bottom: 10px" class="title-font">
          상품명 : {{ live.prdName }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          상품 일련번호 : {{ live.prdNo }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          경매 시작가 : {{ live.prdPriceStart | comma }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          라이브 일시 : {{ live.liveDate.slice(0, 10) }}
          {{ live.liveDate.slice(11, 16) }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          설명 : {{ live.liveDesc }}
        </h5>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import rest from "../js/httpCommon.js";

export default {
  name: "MainCard",
  props: {
    live: Object,
  },
  data: function () {
    return {
      liveprdId: this.live.prdId,
      // lives: [],
      wishlist: [],
      dialog: false,
      clicked: false,
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
        Authorization: ` Bearer ${jwtToken}`,
      };
      return config;
    },
    wish: function () {
      rest
        .axios({
          method: "post",
          url: `/dabid/wish/${this.liveprdId}`,
          headers: this.setToken(),
        })
        .then(() => {
          console.log("wish!!");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    unwish: function () {
      rest
        .axios({
          method: "delete",
          url: `/dabid/wish/${this.liveprdId}`,
          headers: this.setToken(),
        })
        .then(() => {
          console.log("unwish!");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkPrdId: function () {
      console.log(this.live)
      console.log(this.liveprdId)
      rest
        .axios({
          method: "get",
          url: `/dabid/wish/check/${this.liveprdId}`,
          headers: this.setToken()
        })
        .then((res) => {
          this.clicked = res.data;
          console.log(this.clicked)
        })
        .catch((err) => {
          console.log(err);
        });
      },
  },
  // created: function () {
  //   this.checkPrdId()
  // }
};
</script>

<style scoped>
#card-content {
  background-color: #3c3f44;
  color: #dfb772;
}
#card-title {
  font-size: 1rem;
  padding-bottom: 0;
  padding-top: 0;
  color: #dfb772;
}
.headline {
  color: #dfb772;
}
.close {
  position: absolute;
  top: 10px;
  right: 10px;
  opacity: inherit;
}
.live-time {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 160px;
  height: 100%;
  z-index: 1;
}
.font {
  font-size: 1.25rem;
}
</style>


