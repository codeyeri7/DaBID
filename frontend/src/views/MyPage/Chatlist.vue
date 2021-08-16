<template>
  <div class="container" id="app" v-cloak>
    <div class="row">
      <div class="col-md-12">
        <h3>채팅방 리스트</h3>
      </div>
    </div>
    <!-- <div class="input-group">
      <div class="input-group-prepend">
        <label class="input-group-text">방제목</label>
      </div>
      <input type="text" class="form-control" v-model="room_name" v-on:keyup.enter="createRoom">
      <div class="input-group-append">
        <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
      </div>
    </div> -->
    <ul class="list-group">
      <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.live.prdId" v-on:click="enterRoom(item.live.prdId)">
        <span v-if="checkUser(item)">
          {{ item.buyer.userName }} | {{ item.live.prdName }}
        </span>
        <span v-else>
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