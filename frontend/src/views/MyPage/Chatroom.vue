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

export default {
  data() {
    return {
      roomId: '',
      room: {},
      sender: '',
      message: '',
      messages: [],
      stompClient: undefined
    }
  },
  created() {
    this.roomId = this.$route.params.roomId;
    this.sender = this.$route.params.sender;
    this.findRoom();
    this.connect();
  },
  methods: {
    findRoom: function() {
      axios.get('https://localhost:8080/dabid/chat/room/'+this.roomId).then(response => { this.room = response.data; });
    },
    sendMessage: function() {
      let chat =  {
        "type": 'TALK',
        "roomId": this.roomId,
        "sender": this.sender,
        "message": this.message
      };
      this.stompClient.send("/pub/chat/message/", JSON.stringify(chat), {});
      this.message = '';
    },
    // recvMessage: function(recv) {
      // unshift: 배열 앞에 새로운 값 추가
      // this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
      // this.messages.push({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    // },
    connect() {
      const endPoint = "/ws-stomp";
      let sock = new SockJS(endPoint);
      let stompClient = Stomp.over(sock);
      console.log(stompClient);
      // let reconnect = 0;
      // pub/sub event
      stompClient.connect({}, function(frame) {
        console.log('Connected: ', frame);
        stompClient.subscribe("/sub/chat/room/", (res) => {
          var recv = JSON.parse(res.body);
          this.messages.push({"type":recv.type,"sender":recv.sender,"message":recv.message})
          // this.recvMessage(recv);
        });
        // stompClient.send("/pub/chat/message/", JSON.stringify({'type':'ENTER', 'roomId':this.roomId, 'sender':this.sender}), {});
      }.bind(this), function(error) {
        console.log("소켓 연결 실패", error)
        // if(reconnect++ <= 5) {
        //   setTimeout(function() {
        //     console.log("connection reconnect");
        //     sock = new SockJS("/ws-stomp");
        //     ws = Stomp.over(sock);
        //     this.connect();
        //   },10*1000);
        // }
      });
      this.stompClient = stompClient;
    }
  },
}

</script>
