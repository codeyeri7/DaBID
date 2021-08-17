<template>
  <div class="container main-back" id="app" v-cloak>
    <div class="row">
      <div class="col-md-12">
        <h3 id="eng-font" class="gold-color ml-5">Chat Room</h3>
      </div>
    </div>
    <ul class="list-group">
      <li id="kor-font" class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.live.prdId" v-on:click="enterRoom(item.live.prdId)">
        <span v-if="checkUser(item)" class="gold-color">
          {{ item.buyer.userName }} | {{ item.live.prdName }}
        </span>
        <span v-else class="gold-color">
          {{ item.seller.userName }} | {{ item.live.prdName }}
        </span>
      </li>
    </ul>
  </div>
</template>
<script>
import axios from 'axios'
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
        console.log(res.data);
      })
      // axios.get('https://localhost:8080/dabid/chat/rooms').then(response => { this.chatrooms = response.data; });
    },
    enterRoom: function(prdId) {
      // var sender = prompt('대화명을 입력해 주세요.');
      // if(sender != "") {
        // localStorage.setItem('wschat.sender',sender);
        // localStorage.setItem('wschat.roomId',roomId);
        // location.href="/chat/room/enter/"+roomId;
        this.$router.push({
          name: "Chatroom",
          params: {
            // sender: sender,
            prdId: prdId,
          }
        })
      // }
    }
  }
}
</script>
<style scoped>
#app {
  background-color: #151618;
}
.list-group-item {
  background-color: #3c3f44;
  width: 90%;
  border: #3c3f44;
}
</style>