<template>
  <v-dialog v-model="dialog" width="250px">
    <template v-slot:activator="{ on, attrs }">
      <v-card height="300px" width="150px" class="section1">
        <!-- Image -->
        <v-img
          :src="live.prdPhoto"
          class="white--text align-center"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="200px"
          width="200px"
          style="padding: 40px"
          v-bind="attrs"
          v-on="on"
        >
          <!-- <v-card-title class="subtitle-style" style="margin-left:15px;">
            <img @click="remove()" src="@/assets/remove.png" alt="remove live" style="width:20px;height:20px">
          </v-card-title> -->
        </v-img>
        <!-- 카드 하단-->
        <v-card-subtitle class="text-subtitle-4">{{
          live.prdName
        }}</v-card-subtitle>
        <v-card-subtitle class="text-subtitle-4">
          시작가 | {{ live.prdPriceStart | comma }}원
        </v-card-subtitle>
      </v-card>
    </template>
    <v-card :id="prdId">
      <v-card-title class="headline grey lighten-2">
        <h3 class="text-center">{{ live.liveTitle }}</h3>
        <!-- <span v-if="this.prdId === this.wishlist.live.prdId"> -->
        <span v-if="clicked === false">
          <v-col class="text-right">
            <v-btn
              icon
              v-bind:class="{ red: clicked }"
              v-on:click="clicked = !clicked"
              @click="checkWish()"
            >
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-col>
        </span>
        <span v-else>
          <v-col class="text-right">
            <v-btn
              icon
              v-bind:class="{ red: clicked }"
              v-on:click="clicked = !clicked"
              @click="unwish()"
            >
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-col>
        </span>
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
          경매 시작가 : {{ live.prdPriceStart }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          라이브 일시 : {{ live.liveDate }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          설명 : {{ live.liveDesc }}
        </h5>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue" text @click="edit()"> edit </v-btn>
        <v-btn color="red" text @click="remove()"> delete </v-btn>
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
      prdId: this.live.prdId,
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
    remove: function () {
      // this.prdId = this.live.prdId
      rest
        .axios({
          method: "delete",
          url: `/dabid/wish/${this.prdId}`,
        })
        .then((res) => {
          this.refreshAll();
          console.log(res);
          alert("해당 게시글이 삭제되었습니다");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    refreshAll() {
      // 새로고침
      this.$router.go();
    },
    edit: function () {
      this.$router.push({
        name: "UpdateMyLiveList",
        params: { lives: `${this.prdId}` },
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
    getWishList: function () {
      rest
        .axios({
          method: "get",
          url: "/dabid/wish/wishLive",
          headers: this.setToken(),
        })
        .then((res) => {
          this.wishlist = res.data;
          console.log(this.wishlist);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkWish: function () {
      if (this.wishlist.includes()) {
        console.log("있음");
      }
    },
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getWishList();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
};
</script>

<style scoped>
</style>