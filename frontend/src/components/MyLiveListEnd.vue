<template>
   <v-dialog v-model="dialog" width="250px">
    <template v-slot:activator="{ on, attrs }">
      <v-col :cols="6">
        <v-card height="300" tile :elevation="0">
        <!-- Image -->
        <v-img
          :src= live.prdPhoto
          class="white--text align-center"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="180px"
          style="padding: 40px"
          v-bind="attrs"
          v-on="on"
        >
        </v-img>
        <!-- 카드 하단-->
        <div class="card-content" id="kor-font">
              <v-card-title id="card-title">{{ live.prdName  | truncate(8, '...') }}</v-card-title><br>
              <v-card-subtitle class="py-0">시작가 | {{ live.prdPriceStart | comma }}원</v-card-subtitle>
              <v-card-subtitle class="pt-0 pb-1">방송일 | {{ live.liveDate.slice(0,10) }}</v-card-subtitle>
            </div>
        </v-card>
      </v-col>
    </template>
    <v-card :id="prdId">
      <v-card-title class="headline grey lighten-2">
        <h3 class="text-center">{{ live.liveTitle }}</h3>
      </v-card-title>
      <v-card-text>
        <img :src= live.prdPhoto width="200px" class="mt-5">
        <hr>
        <h5 style="margin-bottom:10px" class="title-font">상품명 : {{ live.prdName }}</h5>
        <h5 style="margin-bottom:10px" class="content-font">상품 일련번호 : {{ live.prdNo }}</h5>
        <h5 style="margin-bottom:10px" class="content-font">경매 시작가 : {{ live.prdPriceStart | comma }}</h5>
        <h5 style="margin-bottom:10px" class="content-font">라이브 일시 : {{ live.liveDate.slice(0,10) }} {{ live.liveDate.slice(11, 16)}}</h5>
        <h5 style="margin-bottom:10px" class="content-font">설명 : {{ live.liveDesc }}</h5>
      </v-card-text>
      <!-- <v-divider></v-divider> -->
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="red"
          text
          @click="remove()">
          delete
        </v-btn>
        <v-btn
          color="primary"
          text
          @click="dialog = false"
        >
          close
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import rest from "../js/httpCommon.js"

export default {
  name: 'MyLiveList',
  props: {
    live: Object,
  },
  data: function () {
    return {
      prdId: this.live.prdId,
      lives: [],
      show: false,
      dialog: false,
    }
  },
  filters: {
      comma: function (value) {
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
  },
  methods: {
    remove: function () {
      rest.axios({
        method: 'delete',
        url: '',
      })
        .then((res) => {
          console.log(res)
          this.$router.go();
        })
    }
  },
}
</script>

<style scoped>
</style>