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
          <button class="btn btn-lg btn-primary" id="eng-font" @click="joinSession()">Join!</button>
          <h5 id="kor-font" class="text-center">{{ myUserName }}님이 입장하십니다</h5>
        </p>
        </div>
      </div>
    </div>
    <div id="session" v-if="session">
    <!-- <div id="session"> -->
      <!-- 참가자(Publisher) 화면 -->
      <!-- <div id="video-container" class="col-md-6"> -->
      <div id="video-container">
        <div class="prdInfo" id="session-header">
          <v-card
            class="mx-auto liveInfoCard"
            max-height="150"
            outlined
            id="kor-font"
          >
            <v-list-item three-line>
              <v-list-item-avatar
                tile
                size="70"
                @click="goProfile()"
              ><img src="@/assets/profileImg.png" alt="profile image"/></v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class="font-weight-bold text-h6 mb-1">{{ liveInfo.liveTitle }}</v-list-item-title>
                <v-list-item-subtitle>{{ liveInfo.liveDesc }}</v-list-item-subtitle>
                <v-list-item-subtitle>현재가: {{ currentPrice | comma }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-card>

          
          <CircularCountDownTimer
            ref="countDown"
            :initial-value="10"
            :stroke-width="5"
            :seconds-stroke-color="'#f00'"
            :underneath-stroke-color="'lightgrey'"
            :seconds-fill-color="'#00ffff66'"
            :size="200"
            :padding="14"
            :second-label="'seconds'"
            :show-second="timerShow"
            :show-negatives="true"
            :paused="timerStop"
            :notify-every="'minute'"
        ></CircularCountDownTimer>
          
          <span class="inputTypeToggle" v-if="liveInfo.user.userId == loginId">
            <v-btn style="top:10%" @click="endSession">방종</v-btn>
            <v-btn style="top:20%" @click="goChat">임시 채팅가기</v-btn>
          </span>
        </div>
        <p v-if="liveInfo.user.userId != loginId" id="notice">연속 베팅은 불가능합니다. 10초간 베팅이 없을 시 경매가 종료됩니다.</p>
        <user-video :stream-manager="publisher"/>
        <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        
        <div class="d-flex flex-column comments_wrap">
          <div id="chatList" @scroll="chatOnScroll()">
            <p v-for="(chat, idx) in chatList" :key="idx">
              <span>{{ JSON.parse(chat.from.data).clientData }} </span>
              <v-text><strong>{{ chat.data }}</strong></v-text>
            </p>
          </div>
          <div v-if="isChat">
            <!-- <v-row> -->
              <!-- <v-text-field rounded outlined clearable dense v-model="chatMsg" @keyup.enter="sendMsg" placeholder="질문을 남겨주세요"></v-text-field> -->
              <!-- <v-col cols="12"> -->
                <v-text-field
                  v-model="chatMsg"
                  :append-outer-icon="chatMsg ? 'mdi-arrow-up-circle' : ''"
                  type="text"
                  dense
                  @keyup.enter="sendMsg"
                  @click:append-outer="sendMsg"
                  placeholder="질문을 남겨주세요"
                ></v-text-field>
                <!-- mdi-send
                  rounded outlined dense  -->
              <!-- </v-col> -->
              <!-- <v-btn dark elevation="0" color="primary" @click="sendMsg()" style="height:2rem">전송</v-btn> -->
            <!-- </v-row> -->
          </div>
          <div v-else>
            <!-- <span v-if="liveInfo.user.userId != loginId"> -->
              <!-- <v-row> -->
                <v-text-field
                  :rules="[PriceRules.min, PriceRules.max]"
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
                <!-- <h4 style="text-align:center">원</h4> -->
                <!-- <v-btn dark elevation="0" color="primary" @click="bidding()" style="margin-left:1rem; height:2rem">입찰</v-btn> -->
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="290"
                >
                  <!-- <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      dark elevation="0" color="primary" style="height:2rem"
                      v-bind="attrs"
                      v-on="on"
                    >
                      입찰
                    </v-btn>
                  </template> -->
                  <v-card>
                    <v-card-title class="text-h5">
                      가격을 확인해 주세요.
                    </v-card-title>
                    <v-card-text>
                      현재 입찰가: <strong>{{ this.currentPrice | comma }}</strong>원<br>
                      추가 입찰가: <strong>{{ this.bid | comma }}</strong>원<br>
                      <hr>
                      최종 입찰가: <strong>{{ this.currentPrice + Number(this.bid) | comma }}</strong>원
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        dark
                        color="primary"
                        @click="dialog = false"
                      >
                        취소
                      </v-btn>
                      <!-- <v-btn
                        color="green darken-1"
                        @click="dialog = false"
                      > -->
                      <v-btn
                        dark
                        color="primary"
                        @click="bidding"
                      >
                        확인
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              <!-- </v-row> -->
              <!-- <h5 style="color:red">최소 5,000원 최대 50,000원 까지 입력해주세요.</h5> -->
            <!-- </span> -->
          </div>
        </div>
        <div class="inputTypeToggle" v-if="liveInfo.user.userId != loginId">
          <v-btn class="mx-1" icon @click="isChat=true"><img src="@/assets/chat.png" width="20"></v-btn>
          <v-btn icon @click="isChat=false"><img src="@/assets/bidding.png" width="20"></v-btn>
        </div>
      </div>
      <div style="margin-left: 1.2rem">
        <!-- <span v-if="liveInfo.user.userId != loginId">
          <p style="color:red">연속 베팅은 불가능합니다. 10초간 베팅이 없을 시 경매가 종료됩니다.</p>
        </span>
        <span v-else> -->
        <span class="inputTypeToggle" v-if="liveInfo.user.userId == loginId">
          <!-- <v-btn @click="endSession">방종</v-btn>
          <v-btn @click="goChat">임시 채팅가기</v-btn> -->
          <!-- 마이크 on/off 버튼 -->   
          <v-btn
            icon
            v-if="this.publisher.properties.publishAudio"
            @click="toggleAudio"
            class="toggleBtn"
          >
            <v-icon>mdi-microphone</v-icon>
            <!-- <i class="fas fa-microphone"></i> -->
          </v-btn>
          <v-btn icon v-else @click="toggleAudio" class="toggleBtn">
              <v-icon>mdi-microphone-off </v-icon>
              <!-- <i class="fas fa-microphone-slash"></i> -->
          </v-btn>

          <!-- 카메라 on/off 버튼 -->
          <v-btn
            icon
            v-if="this.publisher.properties.publishVideo"
            @click="toggleVideo"
            class="toggleBtn video"
          >
            <v-icon>mdi-video</v-icon>
            <!-- <i class="fas fa-video"></i> -->
          </v-btn>
          <v-btn icon v-else @click="toggleVideo" class="toggleBtn video">
            <v-icon>mdi-video-off</v-icon>
            <!-- <i class="fas fa-video-slash"></i> -->
          </v-btn>
        </span>
      </div>
      <!-- 본인 화면 
      <div class="video col-md-6" id="main-video">
        <user-video :stream-manager="mainStreamManager"/>
      </div> -->
    <!-- session 닫히는 태그 -->    
    </div>

    <!-- <div class="fixedbutton" style="float: right">
      <RouterLink :to="{ name: 'Main' }">
        <v-btn 
          class="ma-2"
          dark
          color="#FCE4EC"
          id="eng-font"
        >
          <v-icon light left>mdi-arrow-left</v-icon> Back
        </v-btn>
      </RouterLink>
    </div> -->
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../../components/UserVideo.vue";
import rest from "../../js/httpCommon.js";
import CircularCountDownTimer from "../../components/CircularCountDownTimer.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://dabid.ga:443";
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
      currentUser: "",
      PriceRules: {
        // v => /^[0-9]*$/ .test(v) || '금액만 입력해주세요 (20,000원 → 20000)'
        min: (v) => v >= 5000 || "최소 금액은 5,000원 입니다.",
        max: (v) => v <= 50000 || "최대 금액은 50,000원 입니다.",
      },
      loginId: localStorage.getItem("userId"),
      dialog: false,
      isChat: true,
      timerStop: true,
      timerShow: false,
    };
  },
  filters: {
    comma: function (value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
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
    goChat() {
      rest
        .axios({
          url: "/dabid/chat/end/" + this.prdId,
          method: "post",
          data: {
            sellerId: this.liveInfo.user.userId,
            buyerId: this.currentUser,
            // buyerId: "P1628131859142",
            resPriceEnd: this.currentPrice,
            // resPriceEnd: 700000,
          },
        })
        .then((res) => {
          this.leaveSession();
          // this.$router.push("Chatlist");
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

      setTimeout(() => {
        this.session.disconnect();
        this.$router.push({ name: "MyPage" });
      }, 10000);
    },
    toggleAudio() {
      this.publisher.properties.publishAudio =
        !this.publisher.properties.publishAudio;
      this.publisher.publishAudio(this.publisher.properties.publishAudio);
    },
    toggleVideo() {
      //   this.publisher.stream.disposeWebRtcPeer();
      //   this.publisher.stream.disposeMediaStream(); //그냥 아예 종료
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
        console.log(
          "방 폭파ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ"
        );
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        // Receiver of all messages (usually before calling 'session.connect')
        this.session.on("signal", (event) => {
          if (event.type === "signal:BID") {
            this.currentPrice += Number(event.data);
            this.currentUser = JSON.parse(event.from.data).userId;
          } else {
            this.chatList.push(event);
          }
        });
        this.session
          .connect(token, {
            clientData: this.myUserName,
            userId: localStorage.getItem("userId"),
          })
          .then(() => {
            if (this.isPublisher()) {
              // --- Get your own camera stream with the desired properties ---
              let publisher = this.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "360x550", // The resolution of your video
                // 480x640
                // 320x540
                // 360x640
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              this.mainStreamManager = publisher;
              this.publisher = publisher;

              // --- Publish your stream ---

              this.session.publish(this.publisher);
            } else {
              // this.mainStreamManager = publisher;
              console.log("Subscriber입니다....");
            }
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
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
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      window.removeEventListener("beforeunload", this.leaveSession);
      // this.$router.push({ name: 'Main' })
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server   (POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
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

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
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
          console.log("방송 정보", res.data);
          this.liveInfo = res.data;
          // 방송 제목과  받아오기
          this.mySessionId = this.prdId + "";
          this.myUserName = localStorage.getItem("userName");
          if (res.data.logList.length) {
            (this.currentPrice =
              res.data.logList[res.data.logList.length - 1].bidPrice),
              (this.currentUser =
                res.data.logList[res.data.logList.length - 1].bidder);
          }
        })
        .catch((err) => {
          console.log("라이브 정보 받아오기 오류: " + err);
        });
    },
    goProfile() {
      this.$router.push({ name: "MyPage" });
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
      console.log(status); //{"value": 144, "seconds": 24, "minutes": 2, "hours": 0}
    },
  },
  created: function () {
    this.prdId = this.$route.params.prdId;
    console.log(this.prdId + "번 방송입니다.");
    this.getLiveInfo();
    // this.joinSession()
  },
  updated: function () {
    const objDiv = document.getElementById("chatList");
    if (this.bottom_flag) {
      objDiv.scrollTop = objDiv.scrollHeight;
    }
  },
  // mounted() {
  //   window.addEventListener('beforeunload', this.unLoadEvent);
  //   // beforeunload 이벤트는 구체적으로 다음과 같은 상황에서 발생
  //   // 사이트 창을 닫으려고 할 때
  //   // 다른 주소로 이동하려고 할 때
  //   // 같은 주소여도 새로고침하려고 할 때
  // },
  // beforeUnmount() {
  //   window.removeEventListener('beforeunload', this.unLoadEvent);
  // },
  beforeRouteLeave(to, from, next) {
    // to: 이동할 라우터 정보
    // from: 현재 라우터 정보
    // next: 함수.
    // if (this.myRole == 'SUBSCRIBER') {
    // this.leaveSession();
    // next();

    // } else if (confirm('이 사이트에서 나가시겠습니까?\n변경사항이 저장되지 않을 수 있습니다.')) {
    this.leaveSession();
    next();
    // }
  },
};
</script>
<style scoped>
* {
  font-family: "InfinitySans-RegularA1";
}

.form-group {
  /* margin-top :2rem; */
  /* margin-left: 1rem; */
}
/* #currentPrice {
  font-size: 1rem;
  width: 50%;
  font-family: "InfinitySans-RegularA1";
} */
#main-container {
  /* padding-bottom: 0; */
  padding: 0px;
  /* margin-bottom: 60px; */
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
}

.timer {
  height: 500px;
  width: 500px;
  z-index: 1;
}

#chatList {
  height: 7rem;
  /* border-radius: 30px; */
  /* border: 0.2rem solid; */
  margin-bottom: 1.5rem;
  right: 95px;
  left: 15px;
  z-index: 2;
  max-width: 230px;
  overflow-y: scroll;
  max-height: 160px;
  line-height: 1.3;
  font-size: 14px;
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
  /* 잠시 박아놓기 */
  position: absolute;
  top: 100px;
}

.inputTypeToggle {
  position: absolute;
  right: 5%;
  bottom: 13%;
  display: flex;
}

.inputTypeToggle button {
  /* background-color: #151618; */
  background-color: rgba(21, 22, 24, 0.75);
}
</style>