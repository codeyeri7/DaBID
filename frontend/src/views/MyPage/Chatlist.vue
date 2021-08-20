<template>
  <div class="container main-back" id="app" v-cloak>
    <div class="row">
      <div class="col-md-12">
        <h3 id="eng-font" class="gold-color ml-5">Chat Room</h3>
      </div>
    </div>
    <v-col v-for="item in chatrooms" v-bind:key="item.live.prdId">
      <v-card v-on:click="enterRoom(item.live.prdId)" color="gray px-2 py-2" tile elevation="1">
        <div class="chatlist-content d-flex justify-space-between pb-2 px-2" id="kor-font" :style="{'font-size':fontSize1+'px'}">
          <span v-if="checkUser(item)" class="gold-color">
            <v-card-subtitle1>
              {{ item.buyer.userName }} | {{ item.live.prdName }}
            </v-card-subtitle1>
          </span>
          <span v-else class="gold-color">
            <v-card-subtitle1>
              {{ item.seller.userName }} | {{ item.live.prdName }}
            </v-card-subtitle1>
        </span>
        </div>
      </v-card>
    </v-col>
  </div>
</template>
<script>
import rest from "../../js/httpCommon.js"

export default {
  data() {
    return {
      room_name : '',
      chatrooms: [
      ]
    }
  },
  created() {
    this.findAllRoom();
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${jwtToken}`
      }
      return config
    },
    checkUser(item) {
      return item.seller.userId == localStorage.getItem("userId");
    },
    findAllRoom: function() {
      rest.axios({
        url: "dabid/chat/rooms",
        method: "get",
        headers: this.setToken()
      })
      .then(res => {
        this.chatrooms = res.data;
      })
    },
    enterRoom: function(prdId) {
        this.$router.push({
          name: "Chatroom",
          params: {
            prdId: prdId,
          }
        })
    }
  }
}
</script>
<style scoped>
#app {
  background-color: #151618;
}
.chatlist-content {
  color: #dfb772;
}
</style>