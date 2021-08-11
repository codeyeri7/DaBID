<template>
  <div class="container">
      <!-- openvidu --> 
    <div id="join" v-if="!session">
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>Live</h1>
				<div class="form-group">
					<div>
						<label>Seller</label>
						<h5>{{ liveInfo.user.userName }}</h5>
					</div>
					<div>
						<label>Session</label>
						<h5>{{ liveTitle }}</h5>
					</div>
					<div>
						<label>Live Description</label>
						<h5>{{ liveInfo.liveDesc }}</h5>
					</div>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">입장하기</button>
					</p>
				</div>
			</div>
		</div>

		<!-- session live 화면 --> 
		<div id="session" v-if="session">
			<div id="session-header">
				<h1 id="session-title">{{ liveTitle }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave live">
			</div>
			<div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div>
			<!-- video 중복 --> 
			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
			</div>
		</div>
  </div>
</template>

<script>
import { OpenVidu } from 'openvidu-browsfer';
import UserVideo from "../../components/UserVideo.vue"
import axios from 'axios';
import rest from "../../js/httpCommon.js"

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://52.78.142.145:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
    name: 'session',
    components: {
		UserVideo,
	},
    data() {
        return {
            // OpenVidu objects
            OV: undefined,
            session: undefined,
            publisher: undefined, // Local
            subscribers: [], // Remotes

            // liveInfo 받아오고 title, userName만 저장 
			liveInfo: null,
            liveTitle: '',
            userName: '',
			prdId: '',

            // Main video of the page, will be 'publisher' or one of the 'subscribers',
            // updated by click event in UserVideoComponent children
            mainStreamManager: undefined,
        }
    },
    methods: {
         // openvidu
    joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();
			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.liveTitle).then(token => {
				this.session.connect(token, { clientData: this.userName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
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

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},
    leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},
	updateMainVideoStreamManager (stream) {
		if (this.mainStreamManager === stream) return;
		this.mainStreamManager = stream;
	},
    getToken (liveTitle) {
			return this.createSession(liveTitle).then(sessionId => this.createToken(sessionId));
		},
    createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},
    createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
	getLiveInfo () {
		rest.axios({
          method: 'get',
          url:  `/dabid/live/${this.prdId}`,
        })
          .then((res) => {
            console.log('방송 정보', res.data)
            this.liveInfo = res.data
			// 방송 제목 받아오기
			this.liveTitle = res.data.liveTitle
			this.userName = localStorage.getItem('userName')
          })
          .catch((err) => {
            console.log('라이브 정보 받아오기 오류: ' + err)
          })
		}
    },
	created: function () {
		this.prdId = this.$route.params.prdId
		console.log(this.prdId+ '번 방송입니다.')
		this.getLiveInfo()
	}
}
</script>

<style>

</style>