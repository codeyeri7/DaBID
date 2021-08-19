<template>
  <v-dialog v-model="dialog" width="250px">
    <template v-slot:activator="{ on, attrs }">
      <v-col :cols="6">
        <v-card
          color="#3c3f44"
          tile
          :elevation="0"
          light
          height="150"
          v-bind="attrs"
          v-on="on"
        >
          <v-card-title class="pb-1">
            <RouterLink
              :to="{
                name: 'UserProfile',
                params: { userId: hot_live.user.userId },
              }"
            >
              <h4 class="m-0">{{ hot_live.user.userName | truncate(4, "..") }}</h4>
            </RouterLink>
            <div class="ml-6">
              <v-icon class="mr-1" style="color: #dfb772"> mdi-heart </v-icon>
              <span class="subheading mr-2" style="color: #f3e7d0">{{
                hot_live.hearts
              }}</span>
            </div>
          </v-card-title>
          <v-card-text class="card-text" id="kor-font">
            <p class="mb-3">
              <b>{{ hot_live.live.liveTitle | truncate(9, "..") }}</b>
            </p>
            <p>₩ {{ hot_live.live.prdPriceStart | comma }}</p>
            <p>{{ hot_live.live.liveDate.slice(0, 10) }}</p>
          </v-card-text>
        </v-card>
      </v-col>
    </template>

    <v-card :id="prdId">
      <v-card-title class="headline grey lighten-2">
        <h4>{{ hot_live.live.liveTitle }}</h4>
        <span v-if="clicked === false">
          <v-col class="text-right">
            <v-btn icon v-on:click="clicked = !clicked" @click="wish()">
              <v-icon>mdi-heart</v-icon>
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
      </v-card-title>
      <v-card-text>
        <img :src="hot_live.live.prdPhoto" width="200px" class="mt-5" />
        <hr />
        <h5 style="margin-bottom: 10px" class="title-font">
          상품명 : {{ hot_live.live.prdName }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          상품 일련번호 : {{ hot_live.live.prdNo }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          경매 시작가 : {{ hot_live.live.prdPriceStart | comma }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          라이브 일시 : {{ hot_live.live.liveDate.slice(0, 10) }}
          {{ hot_live.live.liveDate.slice(11, 16) }}
        </h5>
        <h5 style="margin-bottom: 10px" class="content-font">
          설명 : {{ hot_live.live.liveDesc }}
        </h5>
      </v-card-text>
      <!-- <v-divider></v-divider> -->
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="edit"> edit </v-btn>
        <v-btn color="red" text @click="remove"> delete </v-btn>
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
    hot_live: Object,
  },
  data: function () {
    return {
      prdId: this.hot_live.live.prdId,
      hot_lives: [],
      wishlist: [],
      dialog: false,
      clicked: false,
      me: null,
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
      const userId = localStorage.getItem("userId");
      if (this.live.user.userId === userId) {
        rest
          .axios({
            method: "delete",
            url: `/dabid/live/${this.prdId}`,
            headers: this.setToken(),
          })
          .then((res) => {
            this.refreshAll();
            console.log(res);
            console.log("삭제 성공");
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        alert("본인이 작성한 글만 삭제 가능합니다!");
      }
    },
    refreshAll: function () {
      // 새로고침
      this.$router.go();
    },
    edit: function () {
      const userId = localStorage.getItem("userId");
      if (this.hot_live.user.userId === userId) {
        this.$router.push({
          name: "UpdateMyLiveList",
          params: { prdId: `${this.prdId}` },
        });
      } else {
        alert("본인이 작성한 글만 수정 가능합니다!");
      }
    },
    // remove: function () {
    //   const userId = localStorage.getItem("userId");
    //   if (this.hot_live.user.userId === userId) {
    //     rest
    //       .axios({
    //         method: "delete",
    //         url: `/dabid/live/${this.prdId}`,
    //         headers: this.setToken(),
    //       })
    //       .then((res) => {
    //         this.refreshAll();
    //         console.log("삭제 성공");
    //       })
    //       .catch((err) => {
    //         console.log(err);
    //       });
    //   } else {
    //     alert("본인이 작성한 글만 삭제 가능합니다!");
    //   }
    // },
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
  created: function () {
    this.checkPrdId();
  },
};
</script>

<style scoped>
p {
  margin-bottom: 0;
  color: #f3e7d0;
}
</style>


