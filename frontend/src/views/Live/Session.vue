<template>
  <div id="main-container" class="container">
		<div id="logged">
			<div id="session">
				<!-- <div id="liveTitle">
					<h1 th:text="${liveTitle}" id="liveTitle"></h1>
					<form action="/leave-session" method="post">
						<input type="hidden" name="liveTitle" th:value="${liveTitle}"></input>
						<input type="hidden" name="token" th:value="${token}"></input>
						<button id="buttonLeaveSession" class="btn btn-large btn-danger" type="submit" onclick="leaveSession()">
							Leave session</button>
					</form>
				</div>
				<div id="main-video" class="col-md-6">
					<p class="userName"></p>
					<p class="userId"></p>
					<video autoplay="autoplay" playsinline="true"></video>
				</div>
				<div id="video-container" class="col-md-6"></div> -->
        <div id="session-header">
          <h1 id="liveTitle">{{ liveTitle }}</h1>
          <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
        </div>
        <div id="main-video" class="col-md-6">
          <user-video :stream-manager="mainStreamManager"/>
        </div>
        <div id="video-container" class="col-md-6">
          <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
          <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
        </div>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import $ from 'jquery';
import UserVideo from '../../components/UserVideo.vue';


axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
  name: 'Session',
  components: {
		UserVideo,
	},
  data() {
    return {
      liveTitle: this.$route.params.liveTitle,
      token: this.$route.params.token,
      userName: this.$route.params.userName,
      userId: this.$route.params.userId,

      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

      myUserId: 'Participant' + Math.floor(Math.random() * 100),
    }
  },
  methods: {
    goSession () {
      // console.warn('Request of TOKEN gone WELL (TOKEN:' + token + ')');

      // --- 1) Get an OpenVidu object ---

      this.OV = new OpenVidu();

      // --- 2) Init a session ---

      this.session = this.OV.initSession();
      console.log('돼용', this.session)

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on('streamCreated', (event) => {

        // Subscribe to the Stream to receive it
        // HTML video will be appended to element with 'video-container' id
        var subscriber = this.session.subscribe(event.stream, 'video-container');

        // When the HTML video has been appended to DOM...
        subscriber.on('videoElementCreated', (event) => {

          // Add a new HTML element for the user's name and nickname over its video
          this.appendUserData(event.element, subscriber.stream.connection);
        });
      });

      // // On every Stream destroyed...
      // this.session.on('streamDestroyed', (event) => {
      //   // Delete the HTML element with the user's name and nickname
      //   this.removeUserData(event.stream.connection);
      // });

      // // On every asynchronous exception...
      // this.session.on('exception', (exception) => {
      //   console.warn(exception);
      // });

      // // On every new Stream received...
			// this.session.on('streamCreated', ({ stream }) => {
      //   console.log('되니?')
			// 	const subscriber = this.session.subscribe(stream);
			// 	this.subscribers.push(subscriber);
			// });
      // console.log('subcribers', this.subscribers)
			// // On every Stream destroyed...
			// this.session.on('streamDestroyed', ({ stream }) => {
			// 	const index = this.subscribers.indexOf(stream.streamManager, 0);
			// 	if (index >= 0) {
			// 		this.subscribers.splice(index, 1);
			// 	}
			// });

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

      // --- 4) Connect to the session passing the retrieved token and some more data from
      //        the client (in this case a JSON with the nickname chosen by the user) ---

      this.session.connect(this.token, { clientData: this.userId })
        .then(() => {

          // --- 5) Set page layout for active call ---

          $('#liveTitle').text(this.liveTitle);
          $('#join').hide();
          $('#session').show();


          // Here we check somehow if the user has 'PUBLISHER' role before
          // trying to publish its stream. Even if someone modified the client's code and
          // published the stream, it wouldn't work if the token sent in Session.connect
          // method is not recognized as 'PUBLIHSER' role by OpenVidu Server
          if (this.isPublisher()) {

            // --- 6) Get your own camera stream ---

            var publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
              publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
              resolution: '640x480',  // The resolution of your video
              frameRate: 30,			// The frame rate of your video
              insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
              mirror: false       	// Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
						this.publisher = publisher;

            // --- 7) Specify the actions when events take place in our publisher ---

            // When our HTML video has been added to DOM...
            publisher.on('videoElementCreated', (event) => {
              // Init the main video with ours and append our data
              var userData = {
                userName: this.userName,
                userId: this.userId
              };
              this.initMainVideo(event.element, userData);
              this.appendUserData(event.element, userData);
              $(event.element).prop('muted', true); // Mute local video
            });


            // --- 8) Publish your stream ---

            this.session.publish(this.publisher);

          } else {
            console.warn('You don\'t have permissions to publish');
            this.initMainVideoThumbnail(); // Show SUBSCRIBER message in main video
          }
        })
        .catch(error => {
          console.warn('There was an error connecting to the session:', error.code, error.message);
        });
    },
    leaveSession() {

      // --- 9) Leave the session by calling 'disconnect' method over the Session object ---
      this.session.disconnect();
    },

    appendUserData(videoElement, connection) {
      var clientData;
      var serverData;
      var nodeId;
      if (connection.userName) { // Appending local video data
        clientData = connection.userName;
        serverData = connection.userId;
        nodeId = 'main-videodata';
      } else {
        clientData = JSON.parse(connection.data.split('%/%')[0]).clientData;
        serverData = JSON.parse(connection.data.split('%/%')[1]).serverData;
        nodeId = connection.connectionId;
      }
      var dataNode = document.createElement('div');
      dataNode.className = "data-node";
      dataNode.id = "data-" + nodeId;
      dataNode.innerHTML = '<p class="userName">' + clientData + '</p><p class="userId">' + serverData + '</p>';
      videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
      this.addClickListener(videoElement, clientData, serverData);
    },
    removeUserData(connection) {
      var userNameRemoved = $("#data-" + connection.connectionId);
      if ($(userNameRemoved).find('p.userName').html() === $('#main-video p.userName').html()) {
        this.cleanMainVideo(); // The participant focused in the main video has left
      }
      $("#data-" + connection.connectionId).remove();
    },
    removeAllUserData() {
      $(".data-node").remove();
    },
    cleanMainVideo() {
      $('#main-video video').get(0).srcObject = null;
      $('#main-video p').each(function () {
        $(this).html('');
      });
    },
    addClickListener(videoElement, clientData, serverData) {
      videoElement.addEventListener('click', function () {
        var mainVideo = $('#main-video video').get(0);
        if (mainVideo.srcObject !== videoElement.srcObject) {
          $('#main-video').fadeOut("fast", () => {
            $('#main-video p.nickName').html(clientData);
            $('#main-video p.userName').html(serverData);
            mainVideo.srcObject = videoElement.srcObject;
            $('#main-video').fadeIn("fast");
          });
        }
      });
    },
    initMainVideo(videoElement, userData) {
      $('#main-video video').get(0).srcObject = videoElement.srcObject;
      $('#main-video p.userName').html(userData.userName);
      $('#main-video p.userId').html(userData.userId);
      $('#main-video video').prop('muted', true);
    },
    initMainVideoThumbnail() {
      $('#main-video video').css("background", "url('images/subscriber-msg.jpg') round");
    },
    isPublisher() {
      return this.userId.includes('test123');
    }      
  },
  // Session.vue 열리자마자 goSession 실행
  created: function() {
    this.goSession()
  },
}
</script>