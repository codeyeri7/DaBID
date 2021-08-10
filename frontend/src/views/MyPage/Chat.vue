<template>
<div>
  <v-container class="fill-height">
    <v-row class="fill-height pb-14" align="end">
      <v-col>
        <div v-for="(item, idx) in recvList" :key="idx" 
            :class="['d-flex flex-row align-center my-2', item.from == 'userName' ? 'justify-end': null]">
          <span v-if="item.from == 'userName'" class="blue--text mr-3">{{ item.message }}</span>
          <v-avatar :color="item.from == 'userName' ? 'indigo': 'red'" size="36">
              <span class="white--text">{{ item.from[0] }}</span>
          </v-avatar>
          <span v-if="item.from != 'userName'" class="blue--text ml-3">{{ item.message }}</span>
        </div>
      </v-col>
    </v-row>
  </v-container>
  <v-form class="chatsend">
    <v-container>
      <v-row>
        <v-col cols="12" class="d-flex flex-row align-center">
          <v-text-field
            v-model="message"
            filled
            label="Message"
            type="text"
          ></v-text-field>
          <v-btn 
            icon 
            class="ml-4" 
            @click="send"
          >
            <v-icon>mdi-send</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</div>

    <!-- 유저이름: 
    <input
      v-model="userName"
      type="text"
    > -->
    <!-- 내용: <input
      v-model="message"
      type="text"
      @keyup="sendMessage"
    > -->
    <!-- <div
      v-for="(item, idx) in recvList"
      :key="idx"
    >
      <h3>유저이름: {{ item.userName }}</h3>
      <h3>내용: {{ item.content }}</h3>
    </div> -->
</template>


<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import $ from "jquery";

export default {
  name: 'Chat',
  data() {
    return {
      userName: "",
      message: "",
      recvList: []
    }
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect()
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },    
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          userName: this.userName,
          content: this.message 
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },    
    connect() {
      const serverURL = "https://localhost:8081"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(this.stompClient)
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );        
    },
  //   connect() {
  //     const socket = new SockJS('/chat');
  //     this.stompClient = Stomp.over(socket);
  //     this.stompClient.connect({}, 
  //       function () {
  //         this.stompClient.subscribe('/topic/' + this.userName, 
  //         function () {
  //           // this.showMessage(JSON.parse(e.body));
  //           this.alertClosing('comeMessage',2000);
  //         });
  //     });
  //   },

  //   send() {
  //     const chatdata = {
  //       chatRoomId: this.chatRoomId,
  //       sender :this.userName,
  //       receiver: this.receiver,
  //       message: $("#message").val()
  //     }
  //     this.stompClient.send("/app/chat/send", {}, JSON.stringify(chatdata));
  //     this.showMessage(chatdata);
  //     $("#message").val('');
  //     this.alertClosing('successMessage',2000);
  //   }
  // }
  }
}
</script>

<style scoped>
  .chatsend{
    position: fixed;
    left:0px;
    bottom:80px;
    height:50px;
    width:100%;
    /* background:rgb(231, 189, 255); 
    color: rgb(255, 255, 255);
    border-radius: 30% 30% 0% 0%; */
  }
</style>