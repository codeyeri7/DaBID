<template>
<!--buyer화면 --> 
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <div id="join-dialog" class="jumbotron vertical-center">
        <h2>Live 입장하기</h2>
        <div class="form-group">
        <p>
          <label id="eng-font">Live Title</label>
          <h4 id="kor-font">{{ liveInfo.liveTitle }}</h4>
        </p>
          <hr>
        <p>
          <label id="eng-font">Live Info</label>
          <h4 id="kor-font">{{ liveInfo.liveDesc }}</h4>
          <h5 id="kor-font"> 시작가 {{ liveInfo.prdPriceStart | comma }}</h5>
        </p>
        <hr>
        <p class="text-center">
          <button class="btn btn-lg gold-color" id="eng-font" @click="joinSession()">Join!</button>
          <h5 id="kor-font" class="text-center"> {{ myUserName }}님이 입장하십니다 </h5>
        </p>
        </div>
      </div>
    </div>
    <div id="session" v-if="session">
      <div id="video-container">
        <div class="prdInfo" id="session-header">
          <v-card
            class="mx-auto liveInfoCard"
            max-height="150"
            max-width="360"
            outlined
            id="kor-font"
          >
            <v-list-item three-line>
                <RouterLink :to="{
                  name: 'UserProfile',
                  params: { userId: liveInfo.user.userId },
                }">
                <v-list-item-avatar
                  tile
                  size="70"
                >
                  <img src="@/assets/profileImg.png" alt="profile image"/>
                </v-list-item-avatar>
              </RouterLink>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold text-h6 mb-1">{{ liveInfo.liveTitle }}</v-list-item-title>
                <v-list-item-subtitle>{{ liveInfo.liveDesc }}</v-list-item-subtitle>
                <v-list-item-subtitle>현재가: {{ currentPrice | comma }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-card>

           <CircularCountDownTimer
            class ="countDownTimer"
            ref="countDown"
            :initial-value="10"
            :stroke-width="5"
            :seconds-stroke-color="'#f97d54'"
            :underneath-stroke-color="'white'"
            :seconds-fill-color="'#f97d54'"
            :padding="14"
            :second-label="'seconds'"
            :show-second="timerShow"
            :show-negatives="true"
            :paused="timerStop"
            :notify-every="'minute'"
          ></CircularCountDownTimer> 
        </div>
        <div>
          <MARQUEE
            scrolldelay="200"
            behavior="scroll"
            v-if="liveInfo.user.userId != loginId && !success"
            id="notice"
          >
            연속 베팅은 불가능합니다. 10초간 베팅이 없을 시 경매가 종료됩니다.
          </MARQUEE>
        </div>

        <!-- 입찰자 생기고 보이기 --> 
        <MARQUEE
            scrolldelay="200"
            behavior="scroll"
            v-if="success"
            id="notice"
          >
          <!-- 판매자한테 보임 --> 
          <p v-if="success && liveInfo.user.userId == loginId">거래 완료. [경매 종료] 버튼을 눌러 입찰자와 채팅을 시작하세요!</p>

          <!-- 시청자한테 보임 --> 
          <p style="color:yello" v-if="success && currentUser == loginId">낙찰 성공하셨습니다!</p>
          <p style="color:yello" v-if="success && liveInfo.user.userId != loginId && currentUser != loginId ">아쉽지만 경매가 종료되었습니다. 30초 후 자동 퇴장됩니다.</p>
        </MARQUEE>

        <p v-if="countDown >= 0" id="noticeCount">{{ countDown }}</p>

        <span v-if="liveInfo.user.userId == loginId" class="fix-btn">
            <v-btn v-if="!auction" id="goChatBtn" @click="startAuction">경매시작</v-btn>
            <v-btn v-else id="goChatBtn" @click="goChat">경매종료</v-btn>
            <v-btn id="exitBtn" icon @click="endSession">X</v-btn>
          </span>

        <user-video :stream-manager="publisher"/>
        <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        
        <div class="d-flex flex-column comments_wrap">
          <div id="chatList" @scroll="chatOnScroll()">
            <p v-for="(chat, idx) in chatList" :key="idx">
              <span>{{ JSON.parse(chat.from.data).clientData }} </span>
              <v-text>{{ chat.data }}</v-text>
            </p>
          </div>
          <div v-if="isChat">
                <v-text-field
                  v-model="chatMsg"
                  :append-outer-icon="chatMsg ? 'mdi-arrow-up-circle' : ''"
                  type="text"
                  dense
                  @keyup.enter="sendMsg"
                  @click:append-outer="sendMsg"
                  placeholder="채팅을 입력해주세요."
                ></v-text-field>
          </div>
          <div v-else>
                <v-text-field
                  :append-outer-icon="bid ? 'mdi-check-circle' : ''"
                  type="number"
                  v-model="bid"
                  dense
                  @keyup.enter="dialog=true"
                  @click:append-outer="dialog=true"
                  placeholder="금액을 입력하세요"
                  hint="최소 5,000원 최대 50,000원"
                  persistent-hint
                  suffix="원"
                ></v-text-field>
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="290"
                >
                  <v-card>
                    <v-card-title class="text-h5">
                      가격을 확인해 주세요.
                    </v-card-title>
                    <v-card-text>
                      현재 입찰가: <strong>{{ this.currentPrice | comma }}</strong>원<br>
                      추가 입찰가: <strong>{{ this.bid | comma }}</strong>원<br>
                      <hr>
                      최종 입찰가: <strong>{{ this.currentPrice + Number(this.bid) | comma }}</strong>원
                      <p v-if="valid == false" class="warning-word">가격 범위 조건을 만족하지 못할 시 입찰 불가합니다. 취소 버튼을 눌러주세요.</p>
                      <p v-if="doublebetting == true" class="warning-word">연속 베팅은 불가합니다. 취소 버튼을 눌러주세요.</p>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        dark
                        color="primary"
                        @click="cancelBid()"
                      >
                        취소
                      </v-btn>
                      <v-btn
                        dark
                        color="primary"
                        @click="bidding"
                        :disabled='doublebetting == true || valid == false'
                      >
                        확인
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
          </div>
        </div>
        <div class="inputTypeToggle" v-if="liveInfo.user.userId != loginId">
          <v-btn color="secondary" class="mx-1" icon @click="isChat=true"><img src="@/assets/chat.png" width="20"></v-btn>
          <v-btn color="secondary" icon @click="isChat=false"><img src="@/assets/bidding.png" width="20"></v-btn>
        </div>
      </div>
      <div style="margin-left: 1.2rem">
        <span class="streamToggle" v-if="liveInfo.user.userId == loginId">
          <!-- 마이크 on/off 버튼 -->   
          <v-btn
            icon
            v-if="this.publisher.properties.publishAudio"
            @click="toggleAudio"
            class="mx-1"
          >
            <v-icon color="primary">mdi-microphone</v-icon>
          </v-btn>
          <v-btn icon v-else @click="toggleAudio" class="mx-1">
              <v-icon color="primary">mdi-microphone-off </v-icon>
          </v-btn>

          <!-- 카메라 on/off 버튼 -->
          <v-btn
            icon
            v-if="this.publisher.properties.publishVideo"
            @click="toggleVideo"
          >
            <v-icon color="primary">mdi-video</v-icon>
          </v-btn>
          <v-btn icon v-else @click="toggleVideo">
            <v-icon color="primary">mdi-video-off</v-icon>
          </v-btn>
        </span>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../../components/UserVideo.vue";
import rest from "../../js/httpCommon.js";
import CircularCountDownTimer from "../../components/CircularCountDownTimer.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://coach48.p.ssafy.io:443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",
  components: {
    UserVideo,
    CircularCountDownTimer,
  },
  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      liveInfo: "",
      prdId: "",
      mySessionId: "",
      myUserName: "",
      myRole: "",

      chatMsg: "",
      chatList: [],
      pre_diffHeight: 0,
      bottom_flag: true,

      bid: "",
      currentPrice: 0,
      loginId: localStorage.getItem("userId"),
      dialog: false,
      isChat: true,

      auction: false,
      timerStop: true,
      timerShow: false,
      currentUser: "",

      valid: true,
      doublebetting: false,
      countDown: 10,
      success: false,
    };
  },
  filters: {
    comma: function (value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    startAuction() {
      this.auction = true;
      this.session.signal({
        data: "auctionStart",
        type: "AUCTION",
      });
    },
    countDownTimer() {
          if (this.countDown > 0) {
              setTimeout(() => {
                this.countDown -= 1;
                this.countDownTimer();
              }, 1000);
            }
          //0초 되면
            else if (this.countDown == 0) {
              // 입찰 축하 멘트 뜸
              this.success = true;
              console.log("이제 30초후에 넌 아웃");
              //30초 후 실행
              setTimeout(() => {
                // 세션 강제 종료
                this.session.disconnect();
                // 채팅 이동
                if (this.currentUser == this.loginId || this.loginId == this.liveInfo.user.userId) {
                  this.goChat();
                } else {
                  this.$router.push({ name: "Main" });
                }
              }, 30000);
            }
    },

    sendMsg: function () {
      this.session
        .signal({
          session: this.mySessionId,
          data: this.chatMsg,
          type: "CHAT",
        })
        .then(() => {
          this.chatMsg = "";
        })
        .catch((error) => {
          console.error(error);
        });
    },
    chatOnScroll: function () {
      const objDiv = document.getElementById("chatList");
      if (objDiv.scrollTop + objDiv.clientHeight == objDiv.scrollHeight) {
        this.bottom_flag = true;
      }
      if (this.pre_diffHeight > objDiv.scrollTop + objDiv.clientHeight) {
        this.bottom_flag = false;
      }
      this.pre_diffHeight = objDiv.scrollTop + objDiv.clientHeight;
    },
    bidding: function () {
      if (this.currentUser == localStorage.getItem("userId")) {
        this.doublebetting = true;
        console.log("연속 베팅은 불가능합니다.");
      } else {
        this.session
          .signal({
            data: this.bid,
            type: "BID",
          })
          .then(() => {
            rest.axios({
              url: "/dabid/live/log",
              method: "post",
              data: {
                prdId: this.prdId,
                bidPrice: this.currentPrice,
                bidder: this.currentUser,
              },
            });
            this.bid = "";
            this.dialog = false;
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    // 취소 버튼 누르면 초기화 시키기
    cancelBid() {
      this.bid = "";
      this.dialog = false;
      this.valid = true;
      this.doublebetting = false;
    },
    goChat() {
      rest
        .axios({
          url: "/dabid/chat/end/" + this.prdId,
          method: "post",
          data: {
            sellerId: this.liveInfo.user.userId,
            buyerId: this.currentUser,
            resPriceEnd: this.currentPrice,
          },
        })
        .then((res) => {
          this.leaveSession();
          this.$router.push({
            name: "Chatroom",
            params: {
              prdId: this.prdId,
            },
          });
        });
    },
    //publisher 입장에서 방송 강제 종료
    endSession() {
      this.timerShow = true;
      this.timerStop = false;
      this.session
        .signal({
          session: this.mySessionId,
          data: this.timershow,
          type: "TIMER",
        })
        .then(() => {
          // 누른 사람만
          setTimeout(() => {
            this.session.disconnect();
            this.$router.push({ name: "MyPage" });
          }, 10000);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    toggleAudio() {
      this.publisher.properties.publishAudio =
        !this.publisher.properties.publishAudio;
      this.publisher.publishAudio(this.publisher.properties.publishAudio);
    },
    toggleVideo() {
      this.publisher.properties.publishVideo =
        !this.publisher.properties.publishVideo;
      this.publisher.publishVideo(this.publisher.properties.publishVideo);
    },
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
        this.$router.push({ name: "Main" });
      });
      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });
      this.getToken(this.mySessionId).then((token) => {
        this.session.on("signal", (event) => {
          if (event.type === "signal:BID") {
            this.currentPrice += Number(event.data);
            this.currentUser = JSON.parse(event.from.data).userId;
            this.countDown = 10;
          } else if (event.type === "signal:CHAT") {
            this.chatList.push(event);
          }
          
          else {
            this.countDownTimer();
          }
        });
        this.session
          .connect(token, {
            clientData: this.myUserName,
            userId: localStorage.getItem("userId"),
          })
          .then(() => {
            if (this.isPublisher()) {
              let publisher = this.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "360x550", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              this.mainStreamManager = publisher;
              this.publisher = publisher;

              this.session.publish(this.publisher);
            } else {
              
            }
          });
      });
      window.addEventListener("beforeunload", this.leaveSession);
    },
    isPublisher() {
      if (this.liveInfo.user.userId == localStorage.getItem("userId")) {
        this.myRole = "PUBLISHER";
      } else {
        this.myRole = "SUBSCRIBER";
      }
      return this.myRole == "PUBLISHER";
    },
    leaveSession() {
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
    getLiveInfo() {
      rest
        .axios({
          method: "get",
          url: `/dabid/live/${this.prdId}`,
        })
        .then((res) => {
          this.liveInfo = res.data;
          this.mySessionId = this.prdId + "";
          this.myUserName = localStorage.getItem("userName");
          if (res.data.logList.length) {
            (this.currentPrice =
              res.data.logList[res.data.logList.length - 1].bidPrice),
              (this.currentUser =
                res.data.logList[res.data.logList.length - 1].bidder);
          } else {
            this.currentPrice = this.liveInfo.prdPriceStart;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    unLoadEvent: function (event) {
      if (this.canLeaveSite) return;

      event.preventDefault();
      event.returnValue = "";
    },
    finished: () => {
      console.log("finished");
    },
    updated: (status) => {
      console.log(status); 
    },
  },
  created: function () {
    this.prdId = this.$route.params.prdId;
    this.getLiveInfo();
    if (this.currentPrice) {
      this.auction = true;
    }
  },
  updated: function () {
    const objDiv = document.getElementById("chatList");
    if (this.bottom_flag) {
      objDiv.scrollTop = objDiv.scrollHeight;
    }
    if (this.dialog == true) {
      if (this.bid < 5000 || this.bid > 50000) {
        this.valid = false;
      }
    }
  },
  beforeRouteLeave(to, from, next) {
   
    this.leaveSession();
    next();
  },
};
</script>
<style scoped>
* {
  font-family: "InfinitySans-RegularA1";
}
#main-container {
  padding: 0px;
}
div.video {
  position: absolute;
  width: 100%;
  height: 500px;
  z-index: 0;
}
div.prdInfo {
  position: absolute;
  z-index: 1;
}
div.button {
  z-index: 1;
}
.comments_wrap {
  position: absolute;
  bottom: 10%;
  margin-left: 15px;
  width: 230px;
}

.countDown {
  z-index: 1;
  position: absolute;
  margin-left: 10%;
  margin-top: 15%;
}
.countDownTimer {
  z-index: 1;
  padding: absolute;
  margin-left: 20%;
  margin-top: 18%;
}
#chatList {
  right: 95px;
  left: 15px;
  z-index: 2;
  max-width: 230px;
  overflow-y: scroll;
  max-height: 160px;
  line-height: 1.3;
  font-size: 12px;
  color: white;
  overscroll-behavior: none;
  will-change: bottom;
}
.liveInfoCard {
  background-color: rgba(255, 255, 255, 0);
  border-color: transparent;
}
#notice {
  color: red;
  position: absolute;
  top: 100px;
}
#noticeCount {
  color: red;
  position: absolute;
  top: 120px;
  margin-left: 30px;
  font-size: 25px;
}
.inputTypeToggle {
  position: absolute;
  right: 5%;
  bottom: 13%;
  display: flex;
}
.inputTypeToggle button {
  background-color: #151618;
}
.warning-word {
  color: red;
  background-color: white;
  font-size: 6px;
}

.streamToggle {
  position: absolute;
  right: 5%;
  bottom: 13%;
  display: flex;
}

#exitBtn {
  color: red;
}

#goChatBtn {
  background-color: #f97d54;
  color: white;
}
.fix-btn {
  z-index: 1;
  position: absolute;
  margin-left: 60%;
  margin-top: 32%;
}
</style>