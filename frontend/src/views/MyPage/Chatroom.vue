<template>
<div>
  <div>
    <v-card tile elevation="1">
      <div class="d-flex justify-content mx-3 mt-4">
        <div>
          <v-img
            :src="room.live.prdPhoto"
            class="white--text align-center mx-2"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="50px"
            width="60px"
            @click="checkPrdId()"
          >
          </v-img>
        </div>
        <div class="d-flex flex-column mx-2" id="kor-font">
          <p>상품명 : {{ room.live.prdName }}</p>
          <p>최종낙찰가 : {{ endlive.resPriceEnd }}원</p>
        </div>
        <span v-if="endlive.seller.userName != sender">
          <v-row
            align="end"
            justify="space-around"
            class="mb-4"
          >
            <v-btn
              tile
              x-small
              color="secondary"
              class="black--text"
              @click="goReview()"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              리뷰작성
            </v-btn>
          </v-row>
        </span>
      </div>
    </v-card>
    <v-container class="fill-height">
      <v-row class="fill-height pb-14" align="end">
        <v-col>
          <div v-for="(message, index) in messages" :key="index" id="kor-font"
              :class="['d-flex flex-row align-center my-2', message.sender == sender ? 'justify-end': null]">
            <span v-if="message.sender == sender" class="black--text mr-3">{{ message.message }}</span>
            <v-avatar :color="message.sender == sender ? 'primary': 'secondary'" size="36">
              <span v-if="message.sender == sender" class="white--text">{{ message.sender[0] }}</span>
              <span v-else class="black--text">{{ message.sender[0] }}</span>
            </v-avatar>
            <span v-if="message.sender != sender" class="black--text ml-3">{{ message.message }}</span>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="fixedchat">
      <v-container>
        <v-row no-gutters>
          <v-col>
            <div class="d-flex flex-row align-center">
              <v-text-field v-model="msg" size="33" placeholder="내용을 입력해주세요" @keypress.enter="sendMessage"></v-text-field>
              <v-btn icon class="ml-4" @click="sendMessage"><v-icon>mdi-send</v-icon></v-btn>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</div>
</template>

<script>
// import axios from 'axios'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import rest from "../../js/httpCommon.js"

export default {
  data() {
    return {
      roomId: '',
      room: {},
      endlive: {},
      sender: '',
      message: '',
      messages: [],
      stompClient: undefined
    }
  },
  created() {
    this.roomId = this.$route.params.prdId;
    // this.sender = this.$route.params.sender;
    this.sender = localStorage.getItem("userName");
    this.findRoom();
    this.getEndLive();
    this.connect();
  },
  methods: {
    findRoom: function() {
      // axios.get('https://localhost:8080/dabid/chat/room/'+this.roomId).then(response => { this.room = response.data; });
      rest.axios({
        url: "dabid/chat/room/"+this.roomId,
      })
      .then(res => {
        console.log(res.data);
        this.room = res.data;
        for (let message of res.data.chatlist) {
          this.messages.push({"type":"TALK","sender":message.chatFrom,"message":message.chatContent})
        }
      })
      .catch(err => {
        console.log(err);
      })
    },
    getEndLive: function() {
      rest.axios({
        url: "dabid/chat/room/"+this.roomId,
      })
      .then(res => {
        console.log(res.data);
        this.endlive = res.data
      })
      .catch(err => {
        console.log(err)
      })
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
        stompClient.subscribe("/sub/chat/room/" + this.roomId, (res) => {
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
    },
    goReview : function () {
      this.$router.push({
        name: "ReviewCreate",
        params: {
          seller : `${this.endlive.seller}`
        }
      })
    }
  },
}
</script>

<style scoped>
.fixedchat {
  position: fixed;
  bottom: 30px;
}
</style>