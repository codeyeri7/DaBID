<template>
  <v-dialog v-model="dialog" width="250px">
    <template v-slot:activator="{ on, attrs }">
      <v-col :cols="6">
        <v-card height="300" tile :elevation="0">
          <!-- Image -->
          <v-img
            :src="live.live.prdPhoto"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="180px"
            style="padding: 40px"
            v-bind="attrs"
            v-on="on"
            @click="checkPrdId()"
          >
          </v-img>
          <!-- 카드 하단-->
          <div class="card-content" id="kor-font">
              <v-card-title id="card-title">{{ live.live.prdName  | truncate(8, '...') }}</v-card-title><br>
              <v-card-subtitle class="py-0">시작가 | {{ live.live.prdPriceStart | comma }}원</v-card-subtitle>
              <v-card-subtitle class="pt-0 pb-1">방송일 | {{ live.live.liveDate | truncate(10, '.') }}</v-card-subtitle>
            </div>
        </v-card>
      </v-col>
    </template>
    <v-card :id="prdId">
      <v-card-title class="headline grey lighten-2">
        <h4>{{ live.live.liveTitle }}</h4>
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
              <v-icon style="color:red">mdi-heart</v-icon>
            </v-btn>
          </v-col>
        </span>
      </v-card-title>
      <v-card-text>
        <img :src="live.live.prdPhoto" width="200px" class="mt-5" />
        <hr />
        <h5 style="margin-bottom: 10px" class="title-font">
          상품명 : {{ live.live.prdName }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          상품 일련번호 : {{ live.live.prdNo }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          경매 시작가 : {{ live.live.prdPriceStart }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          라이브 일시 : {{ live.live.liveDate }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          설명 : {{ live.live.liveDesc }}
        </h5>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="dialog = false"> close </v-btn>
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
      prdId: this.live.live.prdId,
      lives: [],
      wishlist: [],
      show: false,
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
</style>