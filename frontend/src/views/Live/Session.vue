<template>
  <div id="main-container" class="container">
    <div id="logged">

      <div id="session">
        <div id="liveTitle" class="row">
          <div class="col-8">
            <h3 text="${liveTitle}" id="liveTitle">{{ this.liveTitle }}</h3>
          </div>
          <div class="col-4">
            <!-- <v-btn id="buttonLeaveSession" class="btn btn-large btn-danger" @click="leaveSession">
              Leave session</v-btn> -->
            <v-btn icon class="btn btn-danger" id="buttonLeaveSession" @click="leaveSession">
              <i class="fas fa-times"></i>
            </v-btn>

          </div>
				</div>
				<div id="main-video" class="col-md-6">
					<!-- <p class="userName"></p>
					<p class="userId"></p>
					<video autoplay="autoplay" playsinline="true"></video> -->

          <!-- 마이크 on/off 버튼 -->
          <v-btn icon v-if="this.publisher.properties.publishAudio"
                 @click="toggleAudio" class="toggleBtn">
            <i class="fas fa-microphone"></i>
          </v-btn>
          <v-btn icon v-else @click="toggleAudio" class="toggleBtn">
            <i class="fas fa-microphone-slash"></i></v-btn>

          <!-- 카메라 on/off 버튼 -->
          <v-btn icon v-if="this.publisher.properties.publishVideo"
                  @click="toggleVideo" class="toggleBtn video">
            <i class="fas fa-video"></i>
          </v-btn>
          <v-btn icon v-else @click="toggleVideo" class="toggleBtn video">
            <i class="fas fa-video-slash"></i></v-btn>

          <!-- 메인 카메라 -->
          <user-video :stream-manager="mainStreamManager"></user-video>

				</div>
				<div id="video-container" class="col-md-6">
          <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
          <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
  .toggleBtn {
    position: absolute;
    z-index: 1;
  }
  .toggleBtn.video {
    left: 30px;
  }
</style>

<script>
import axios from "axios";
import rest from "../../js/httpCommon.js"
import { OpenVidu } from "openvidu-browser";
import $ from "jquery";
import UserVideo from "./UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "Session",
  components: {
    UserVideo,
  },
  data() {
    return {
      liveTitle: this.$route.params.liveTitle,
      token: this.$route.params.token,
      userName: this.$route.params.userName,
      userId: this.$route.params.userId,
      role: this.$route.params.role,

      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      myUserId: "Participant" + Math.floor(Math.random() * 100),
    };
  },
  methods: {
    goSession() {
      // console.warn('Request of TOKEN gone WELL (TOKEN:' + token + ')');

      // --- 1) Get an OpenVidu object ---

      this.OV = new OpenVidu();

      // --- 2) Init a session ---

      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---
      // 세션에서 이벤트 발생할 때의 액션들 지정

      // On every new Stream received...
      // streamCreated: session publishing을 시작했을 때
      this.session.on("streamCreated", (event) => {
        // Subscribe to the Stream to receive it
        // HTML video will be appended to element with 'video-container' id

        // <div id="video-container">에 <video> append
        var subscriber = this.session.subscribe(
          event.stream,
          "video-container"
        );
        this.subscribers.push(subscriber);

        // When the HTML video has been appended to DOM...
        // <video>가 DOM에 붙여진다면...
        subscriber.on("videoElementCreated", (event) => {
          // Add a new HTML element for the user's name and nickname over its video
          
          // <p class="userName"> 유저 닉네임과
          // <p class="userId"> 유저 아이디
          this.appendUserData(event.element, subscriber.stream.connection);
        });
      });

      // On every Stream destroyed...
      // streamDestroyed: session publishing을 멈췄을 때
      this.session.on('streamDestroyed', (event) => {
        // Delete the HTML element with the user's name and nickname
        this.removeUserData(event.stream.connection);
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- 4) Connect to the session passing the retrieved token and some more data from
      //        the client (in this case a JSON with the nickname chosen by the user) ---
      // 백에서 받아온 토큰과 데이터를 이용해 세션에 연결
      this.session
        .connect(this.token, { clientData: this.userId })
        .then(() => {
          // --- 5) Set page layout for active call ---

          // 방제목 설정해주기 => 필요없음
          // $("#liveTitle").text(this.liveTitle);

          // #join이 뭔지 모르겠음
          $("#join").hide();
          // 숨겨져있던 방제목과 비디오 보여주기
          $("#session").show();
          

          // Here we check somehow if the user has 'PUBLISHER' role before
          // trying to publish its stream. Even if someone modified the client's code and
          // published the stream, it wouldn't work if the token sent in Session.connect
          // method is not recognized as 'PUBLIHSER' role by OpenVidu Server

          // 방송 송출 전 유저가 'PUBLISHER'인지 체크
          if (this.isPublisher()) {
            // --- 6) Get your own camera stream ---

            var publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam

              // 나중에 버튼 만들어서 이걸로 마이크 비디오 켜고 끄면 될 것 같음
              // this.publisher.publishAudio(false);
              // this.publisher.publishVideo(false);
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not

              resolution: "360x640", // The resolution of your video
              frameRate: 30, // The frame rate of your video

              // <div id="video-container">에 <video>를 어떻게 추가할 건지 옵션 설정
              // AFTER, APPEND(default), BEFORE, PREPEND, REPLACE
              insertMode: "REPLACE", // How the video is inserted in the target element 'video-container'
              // 좌우 반전
              mirror: true, // Whether to mirror your local video or not
            });

            // mainStreamManager: 하위 컴포넌트인 UserVideo에 넘기기위함
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- 7) Specify the actions when events take place in our publisher ---

            // When our HTML video has been added to DOM...
            // <video>가 추가됐을 때
            publisher.on("videoElementCreated", (event) => {
              // Init the main video with ours and append our data
              // 메인 비디오를 우리껄로 초기화하고 우리의 데이터를 붙여?
              var userData = {
                userName: this.userName,
                userId: this.userId,
              };
              this.initMainVideo(event.element, userData);
              this.appendUserData(event.element, userData);
              $(event.element).prop("muted", true); // Mute local video
            });

            // --- 8) Publish your stream ---
            // 설정 끝났고 드디어 publish!
            this.session.publish(this.publisher);
            console.log("설정 끝났고 드디어 publish!")
          } else {
            console.warn("You don't have permissions to publish");
            this.initMainVideoThumbnail(); // Show SUBSCRIBER message in main video
            this.updateMainVideoStreamManager(this.publisher);
            this.session.subscribe();
          }
        })
        .catch((error) => {
          console.warn(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });
      window.addEventListener('beforeunload', this.leaveSession);
    },
    // leaveSession() {
    //   // --- 9) Leave the session by calling 'disconnect' method over the Session object ---
    //   this.session.disconnect();
    // },

    leaveSession () {
      console.log("leaveSession()");
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);

      //  action="/dabid/leave-session" method="post"
      rest.axios({
        method: "post",
        url: 'dabid/leave-session',
        data: {
          token: this.token,
          liveTitle: this.liveTitle
        }
      })
      .then(res => {
        console.log(res.data);
      })
      .catch(err => {
        console.error(err);
      })
		},


    appendUserData(videoElement, connection) {
      var clientData;
      var serverData;
      var nodeId;
      if (connection.userName) {
        // Appending local video data
        clientData = connection.userName;
        serverData = connection.userId;
        nodeId = "main-videodata";
      } else {
        clientData = JSON.parse(connection.data.split("%/%")[0]).clientData;
        serverData = JSON.parse(connection.data.split("%/%")[1]).serverData;
        nodeId = connection.connectionId;
      }
      var dataNode = document.createElement("div");
      dataNode.className = "data-node";
      dataNode.id = "data-" + nodeId;
      dataNode.innerHTML =
        '<p class="userName">' +
        clientData +
        // '</p><p class="userId">' +
        // serverData +
        "</p>";
      videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
      this.addClickListener(videoElement, clientData, serverData);
    },
    removeUserData(connection) {
      var userNameRemoved = $("#data-" + connection.connectionId);
      if (
        $(userNameRemoved).find("p.userName").html() ===
        $("#main-video p.userName").html()
      ) {
        this.cleanMainVideo(); // The participant focused in the main video has left
      }
      $("#data-" + connection.connectionId).remove();
    },
    removeAllUserData() {
      $(".data-node").remove();
    },
    cleanMainVideo() {
      $("#main-video video").get(0).srcObject = null;
      $("#main-video p").each(function () {
        $(this).html("");
      });
    },
    addClickListener(videoElement, clientData, serverData) {
      // 비디오 클릭하면 메인 비디오에 올리기 fadeOut/In
      videoElement.addEventListener("click", function () {
        var mainVideo = $("#main-video video").get(0);
        if (mainVideo.srcObject !== videoElement.srcObject) {
          $("#main-video").fadeOut("fast", () => {
            $("#main-video p.nickName").html(clientData);
            $("#main-video p.userName").html(serverData);
            mainVideo.srcObject = videoElement.srcObject;
            $("#main-video").fadeIn("fast");
          });
        }
      });
    },
    initMainVideo(videoElement, userData) {
      // 메인 비디오는 가장 처음에 있는 사람 비디오!
      $("#main-video video").get(0).srcObject = videoElement.srcObject;
      $("#main-video p.userName").html(userData.userName);
      // $("#main-video p.userId").html(userData.userId);
      $("#main-video video").prop("muted", true);
    },
    initMainVideoThumbnail() {
      $("#main-video video").css(
        "background",
        "url('images/subscriber-msg.jpg') round"
      );
    },
    isPublisher() {
      console.log("Session.vue: Publisher 확인")
      // return this.userId.includes("P");
      return this.role.includes("PUBLISHER");
    },
    unLoadEvent(event) {
      if (this.canLeaveSite) return;

      event.preventDefault();
      event.returnValue = '';
    },
    toggleAudio() {
      this.publisher.properties.publishAudio = !this.publisher.properties.publishAudio;
    },
    toggleVideo() {
      this.publisher.properties.publishVideo = !this.publisher.properties.publishVideo;
    },
  },
  // Session.vue 열리자마자 goSession 실행
  created: function () {
    console.log("Session.vue created()")
    console.log("liveTitle: "+this.liveTitle);
    this.goSession();
  },
  mounted() {
    window.addEventListener('beforeunload', this.unLoadEvent)
  },
  beforeUnmount() {
    window.removeEventListener('beforeunload', this.unLoadEvent);
  },
  unLoadEvent(event) {
    event.preventDefault();
    event.returnValue = "";
  },
  beforeRouteLeave(to, from, next) {
    // to: 이동할 라우터 정보
    // from: 현재 라우터 정보
    // next: 함수. 인자에 공식 홈페이지에서 제공하는 값을 넣을 수 있음.
    // 인자를 넣지 않으면 다음 훅 실행. next는 사이클 안에 한 번만 실행.
    if (this.role.includes("SUBSCRIBER")) {
      this.leaveSession();
      next();
    }
    else if (confirm('이 사이트에서 나가시겠습니까?\n진행 중인 라이브가 종료됩니다.')) {
      this.leaveSession();
      next();
    }
  }
};
</script>