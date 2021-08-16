<template>
  <v-dialog v-model="dialog" width="250px">
    <template v-slot:activator="{ on, attrs }">
      <v-col :cols="6">
        <v-card height="300" tile :elevation="0" v-bind="attrs"
            v-on="on">
          <!-- Image -->
          <v-img
            :src="live.prdPhoto"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="180px"
            style="padding: 40px"
            @click="checkPrdId()"
          >
          </v-img>
          <!-- 카드 하단-->
          <div class="card-content" id="kor-font">
              <v-card-title id="card-title">{{ live.prdName | truncate(8, '...') }}</v-card-title><br>
              <v-card-subtitle class="py-0">시작가 | {{ live.prdPriceStart | comma }}원</v-card-subtitle>
              <v-card-subtitle class="pt-0 pb-1">방송일 | {{ live.liveDate.slice(0,10) }}</v-card-subtitle>
            </div>
        </v-card>
      </v-col>
    </template>
    <v-card :id="prdId">
      <v-card-title class="headline grey lighten-2">
        <h4>{{ live.liveTitle }}</h4>
        <span v-if="clicked === false">
          <v-col class="text-right">
            <v-btn
              icon
              v-on:click="clicked = !clicked"
              @click="wish()"
            >
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-col>
        </span>
        <span v-else>
          <v-col class="text-right">
            <v-btn
              icon
              v-on:click="clicked = !clicked"
              @click="unwish()"
            >
            <!-- v-bind:class="{ red: clicked }" -->
              <v-icon style="color:red">mdi-heart</v-icon>
            </v-btn>
          </v-col>
        </span>
      <v-btn color="primary" text @click="dialog = false"> X </v-btn>
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
          라이브 일시 : {{ live.liveDate.slice(0,10) }} {{ live.liveDate.slice(11, 16)}}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          설명 : {{ live.liveDesc }}
        </h5>
      </v-card-text>
      <!-- <v-divider></v-divider> -->
      <v-card-actions>
        <v-spacer></v-spacer>
        <div v-if="checkUser(live)">
          <v-btn color="primary" text @click="goLive(live.prdId)"> Start Live </v-btn>
          <v-btn color="blue darken-1" text @click="edit()"> edit </v-btn>
          <v-btn color="red" text @click="remove()"> delete </v-btn>
        </div>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import rest from "../js/httpCommon.js";

export default {
  name: "MyLiveList",
  props: {
    live: Object,
  },
  data: function () {
    return {
      prdId: this.live.prdId,
      lives: [],
      wishlist: [],
      dialog: false,
      clicked: false,
      me: null
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
    remove: function () {
      rest
        .axios({
          method: "delete",
          url: `/dabid/live/${this.prdId}`,
          headers: this.setToken()
        })
        .then((res) => {
          this.refreshAll();
          console.log(res)
          console.log('삭제 성공')
        })
        .catch((err) => {
          console.log(err)
        })
    },
    refreshAll: function () {
      // 새로고침
      this.$router.go();
    },
    edit: function () {
        this.$router.push({
          name: "UpdateMyLiveList",
          params: { prdId: `${this.prdId}` },
        });
    },
    checkUser(live) {
      const userId = localStorage.getItem('userId')
      return live.user.userId == userId
    },
    goLive (prdId) {
      this.$router.push({
        name: "Seller", 
        params: {
          prdId : prdId
        }
      });
    },
    wish: function () {
      rest
        .axios({
          method: "post",
          url: `/dabid/wish/${this.prdId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          console.log("wish!!");
          console.log(this.wishlist.includes(this.prdId));
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    unwish: function () {
      rest
        .axios({
          method: "delete",
          url: `/dabid/wish/${this.prdId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          console.log("unwish!");
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkPrdId: function () {
      rest
        .axios({
          method: "get",
          url: `/dabid/wish/check/${this.prdId}`,
          headers: this.setToken(),
        })
        .then((res) => {
          this.clicked = res.data;
          console.log("OK!");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  }, 
};
</script>

<style scoped>
#card-content {
  background-color: #FDF4F4;
  opacity: 0.8;
}
#card-title {
  font-size: 1rem;
  padding-bottom: 0;
  padding-top: 0;
  color:black;
}
</style>


