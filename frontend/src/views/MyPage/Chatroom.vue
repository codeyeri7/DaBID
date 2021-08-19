<template>
  <div class="chat-back">
    <v-card color="secondary" tile elevation="1">
      <div class="d-flex justify-content mx-3">
        <div>
          <v-img
            :src="room.live.prdPhoto"
            class="white--text align-center mx-2 mt-4"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="50px"
            width="60px"
            @click="checkPrdId()"
          >
          </v-img>
        </div>

        <div class="d-flex flex-column mx-2 gold-color pt-2" id="kor-font">
          <p>상품명 : {{ room.live.prdName }}</p>
          <p>최종낙찰가 : {{ endlive.resPriceEnd | comma }}원</p>
        </div>

        <!-- 확인 때문에 임시로 같다로 조건 바꿔뒀어요 --> 
        <div v-if="endlive.seller.userName != sender">
          <v-btn
            tile
            x-small
            color="primary"
            class="black--text mt-5 ml-3"
            width="80px"
            id="kor-font"
            @click="goReview(room.live.prdId)"
          >
            <v-icon left color="black">
              mdi-pencil
            </v-icon>
            리뷰작성
          </v-btn>
          <v-dialog
            v-model="dialog"
            width="500"
          >
            <template v-slot:activator="{ on, attrs }">
              <!-- <RouterLink :to="{ name: 'TheCheat' }"> -->
              <v-btn
                v-bind="attrs"
                v-on="on"
                tile
                x-small
                color="primary"
                class="black--text mt-5 ml-3"
                width="80px"
                id="kor-font">
                더치트 확인
              </v-btn>
            </template>
              <!-- modal --> 
            <div class=" d-flex align-center text-center">
              <v-card tile class="ap-card" style="color:#dfb772; background-color: #151618" >
                <v-card-title  tile class="text-h5">
                  Accident Policy
                </v-card-title>

                <v-card-text style="padding-top: 2.5rem;color:#dfb772" id="kor-font">
                  다비드는 원활한 거래 연결을 위해<br>
                  더치트 계좌 조회 서비스를 지원합니다.
                  <v-btn 
                    color="#3c3f44" 
                    tile 
                    class="mt-4 link-button" 
                    onclick="window.open('https://thecheat.co.kr/rb/?r=home&mod=_thecheat_validity_account') "
                  >
                    위험 계좌조회 더치트 바로가기
                  </v-btn>
                </v-card-text>
                <v-divider></v-divider>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="primary"
                    text
                    @click="dialog = false"
                  >
                    확인
                  </v-btn>
                </v-card-actions>
              </v-card>
            </div>
          </v-dialog>
        </div>
      </div>
    </v-card>

    <v-container style="background-color:#3c3f44">
      <v-row align="end">
        <v-col>
          <div class="comments_wrap" id="chatList" @scroll="chatOnScroll()">
            <div v-for="(message, index) in messages" :key="index" id="kor-font"
              :class="['d-flex flex-row align-center my-2', message.sender == sender ? 'justify-end': null]">
              <span v-if="message.sender == sender" class="white--text mr-3 chat">{{ message.message }}</span>

                <v-avatar v-if="message.sender != sender" color="primary" size="36">
                  <span class="white--text">{{ message.sender[0] }}</span>
                </v-avatar>
              <span v-if="message.sender != sender" class="white--text ml-3 chat">{{ message.message }}</span>
              
            </div>
          </div>      
        </v-col>
      </v-row>
    </v-container>
    <div class="fixedchat">
      <v-container style="padding-top:0">
        <v-row no-gutters>
          <v-col>
            <div class="d-flex flex-row align-center">
              <v-text-field class="gold-color" v-model="message" size="33" placeholder="내용을 입력해주세요" @keypress.enter="sendMessage"></v-text-field>
              <v-btn icon class="ml-4" @click="sendMessage"><v-icon color="primary">mdi-send</v-icon></v-btn>
            </div>
          </v-col>
        </v-row>
      </v-container>
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
      stompClient: undefined,
      pre_diffHeight: 0,
      bottom_flag: true,

      dialog: false,
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
  filters: {
    comma: function (value) {
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
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
        console.log('거래완료한 방송 정보', res.data);
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
    chatOnScroll: function () {
      const objDiv = document.getElementById("chatList");
      if((objDiv.scrollTop + objDiv.clientHeight) == objDiv.scrollHeight) {
        this.bottom_flag = true;
      }
      if(this.pre_diffHeight > objDiv.scrollTop + objDiv.clientHeight) {
        this.bottom_flag = false;
      }
      this.pre_diffHeight = objDiv.scrollTop + objDiv.clientHeight
    },
    // recvMessage: function(recv) {
      // unshift: 배열 앞에 새로운 값 추가
      // this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
      // this.messages.push({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    // },
    connect() {
      const endPoint = "http://i5a506.p.ssafy.io/ws-stomp";
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
    goReview : function (Id) {
      console.log('이 정보 넘길거다', this.endlive.seller, Id)
      this.$router.push({
        name: "ReviewCreate",
        params: {
          seller : this.endlive.seller,
          prdId : Id
        }
      })
    }
  },
  updated: function () {
    const objDiv = document.getElementById("chatList");
    if(this.bottom_flag){
      objDiv.scrollTop = objDiv.scrollHeight;
    }
  }
}
</script>

<style scoped>
.fixedchat {
  position: fixed;
  bottom: 30px;
  background-color: white;
  height: 90px;
}
.chat {
  background-color: #151618;
  padding: 10px;
  border-radius: 10px;
}
.chat-back {
  background-color: #3c3f44;
  min-height: 100%;
  margin-bottom: 50px;
}
.comments_wrap {
  margin-bottom: 1.5rem;
  background-color: #3c3f44;
  z-index: 2;
  overflow-y: scroll;
  max-height: 350px;
  line-height: 1.3;
  font-size: 14px;
  color: white;
  overscroll-behavior: none;
  will-change: bottom;
}
.link-button {
  color: #dfb772;
}
.v-application--wrap {
  min-height: 0vh !important;
}
</style>