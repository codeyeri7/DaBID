<template>
  <div class="container" id="app" v-cloak>
    <div>
      <h2>{{room.name}}</h2>
    </div>
    <div class="input-group">
      <div class="input-group-prepend">
        <label class="input-group-text">내용</label>
      </div>
      <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
      <div class="input-group-append">
          <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
      </div>
    </div>
    <ul class="list-group">
      <li class="list-group-item" v-for="(message, idx) in messages" :key="idx" :message="message">
        <a>{{message.sender}} - {{message.message}}</a>
      </li>
    </ul>
    <div></div>
  </div>
</template>

<script>
import axios from 'axios'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client' 

var sock = new SockJS("/ws-stomp");
var ws = Stomp.over(sock);
var reconnect = 0;

export default {
  data() {
    return {
      roomId: '',
      room: {},
      sender: '',
      message: '',
      messages: []
    }
  },
  created() {
    this.roomId = localStorage.getItem('wschat.roomId');
    this.sender = localStorage.getItem('wschat.sender');
    this.findRoom();
  },
  methods: {
    findRoom: function() {
        axios.get('/chat/room/'+this.roomId).then(response => { this.room = response.data; });
    },
    sendMessage: function() {
        ws.send("/pub/chat/message", {}, JSON.stringify({type:'TALK', roomId:this.roomId, sender:this.sender, message:this.message}));
        this.message = '';
    },
    recvMessage: function(recv) {
        this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    }
  },
  connect() {
    // pub/sub event
    ws.connect({}, function(frame) {
      ws.subscribe("/sub/chat/room/"+ vm.$data.roomId, function(message) {
          var recv = JSON.parse(message.body);
          vm.recvMessage(recv);
      });
      ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, sender:vm.$data.sender}));
    }, function(error) {
      if(reconnect++ <= 5) {
        setTimeout(function() {
          console.log("connection reconnect");
          sock = new SockJS("/ws-stomp");
          ws = Stomp.over(sock);
          this.connect();
        },10*1000);
      }
    });
  }
}
</script>
