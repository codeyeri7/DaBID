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
			<div id="session-header">
				<v-card
					class="mx-auto"
					max-height="150"
					outlined
					id="kor-font"
				>
					<v-list-item three-line>
					<v-list-item-content>
						<div class="text-overline mb-2">
						{{ liveInfo.productCategory.prdCategoryName }}
						</div>
						<v-list-item-title class="text-h6 mb-1">
						{{ liveInfo.liveTitle }}
						</v-list-item-title>
						<v-list-item-subtitle>{{ liveInfo.liveDesc }}</v-list-item-subtitle>
					</v-list-item-content>
					<v-list-item-avatar
						tile
						size="70"
						@click="goProfile()"
					><img src="@/assets/profileImg.jpg" alt="profile image"/></v-list-item-avatar>
					</v-list-item>
				</v-card>
			</div>
				<div style="margin-left: 1.2rem">
					<p id="currentPrice">현재가: {{ currentPrice | comma }}</p>
					<v-btn @click="endSession">방종</v-btn>
					<v-btn @click="goChat">임시 채팅가기</v-btn>
				</div>
			
			<!-- 본인 화면 --> 
			<div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div>
			<!-- 무슨 화면 -->
			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
				<!-- <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/> -->
			</div>
			
			<div class="chat">
				<div class="chat-list">
					<p v-for="(chat, idx) in chatList" :key="idx">
						<span>{{ myUserName }}: </span>
						<v-text>{{ chat }}</v-text>
						</p>
				</div>

				<v-row style="width: 80%; margin-left:1.5rem">
					<v-text-field type="text" style="width:60%" v-model="chatMsg" @keyup.enter="sendMsg" placeholder="질문을 남겨주세요"></v-text-field>
					<v-btn dark elevation="0" color="primary" @click="sendMsg()" style="height:2rem">전송</v-btn>
				</v-row>
				<br>
			</div>
		<!-- session 닫히는 태그 --> 	
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../../components/UserVideo.vue';
import rest from "../../js/httpCommon.js"

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://dabid.ga:443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'Seller',

	components: {
		UserVideo,
	},

	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

			liveInfo: '',
			prdId: '',
			mySessionId: '',
			myUserName: '',

			chatMsg: '',
			chatList: [],

			bid: '',
			currentPrice: 0,
			currentUser: '',
			PriceRules: [
			v => /^[0-9]*$/ .test(v) || '금액만 입력해주세요 (20,000원 → 20000)'
      ],
		}
	},
	filters: {
		comma: function (value) {
			return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	},
	methods: {
		sendMsg() {
			return new Promise((resolve, reject) => {
				axios({
					method: "post",
					url: `${OPENVIDU_SERVER_URL}/openvidu/api/signal`,
					auth: {
						username: 'OPENVIDUAPP',
						password: OPENVIDU_SERVER_SECRET,
					},
					data: {
						session: this.mySessionId,
						data: this.chatMsg,
						type: "CHAT",
					}
				})
				.then(response => response.data)
				.then(data => resolve(data.token))
				.catch(error => reject(error.response));
			});
		},
		endSession() {
			
		},
		goChat() {
			rest.axios({
				url: "/dabid/chat/end/"+this.prdId,
				method: "post",
				data: {
					sellerId: this.liveInfo.user.userId,
					buyerId: this.currentUser,
					// buyerId: "P1628131859142",
					resPriceEnd: this.currentPrice,
					// resPriceEnd: 700000,
				}
			})
			.then(res => {
				this.leaveSession();
				// this.$router.push("Chatlist");
				this.$router.push({
					name: "Chatroom",
					params: {
						prdId: this.prdId,
					}
				})
			})
		},
		bidding() {
			return new Promise((resolve, reject) => {
				axios({
					method: "post",
					url: `${OPENVIDU_SERVER_URL}/openvidu/api/signal`,
					auth: {
						username: 'OPENVIDUAPP',
						password: OPENVIDU_SERVER_SECRET,
					},
					data: {
						session: this.mySessionId,
						data: this.bid,
						type: "BID",
					}
				})
				.then(response => response.data)
				.then(data => resolve(data.token))
				.catch(error => reject(error.response));
			});
		},
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
			this.getToken(this.mySessionId).then(token => {

				// // Receiver of the message (usually before calling 'session.connect')
				// this.session.on('signal:my-chat', (event) => {
				// 	console.log(event.data); // Message
				// 	console.log(event.from); // Connection object of the sender
				// 	console.log(event.type); // The type of message ("my-chat")
				// });

				// Receiver of all messages (usually before calling 'session.connect')
				this.session.on('signal', (event) => {
					console.log(event.type + ": ")
					console.log(event.data); // Message
					if (event.type === "signal:BID") {
						this.currentPrice = event.data;
						this.currentUser = event.from;
						console.log('방금 입력한 놈', this.currentUser)
					} else {
						this.chatList.push(event.data);
					}
					// console.log(event.from); // Connection object of the sender
					// console.log(event.type); // The type of message
				});

				this.session.connect(token, { clientData: this.myUserName })
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

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
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

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
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
				// 방송 제목과  받아오기
				this.mySessionId = this.prdId + ''
				this.myUserName = localStorage.getItem('userName')
			})
			.catch((err) => {
				console.log('라이브 정보 받아오기 오류: ' + err)
			})
		},
		goProfile () {
			this.$router.push({ name: "MyPage" });
		}
	},
	created: function () {
		this.prdId = this.$route.params.prdId
		console.log(this.prdId+ '번 방송입니다.')
		this.getLiveInfo()
		this.joinSession()
	}
}
</script>
<style scoped>
.form-group {
	margin-top :2rem;
	margin-left: 1rem;
}
#currentPrice {
	font-size: 1rem;
	color: red;
	font-weight: bold;
	width: 50%;
	margin-top: 2rem;
	text-align: center;
	font-family: "InfinitySans-RegularA1";
}
#main-container {
	padding-bottom: 0;
}
.chat-list {
	height: 7rem;
	border-radius: 30px;
	border: 0.2rem solid;
	margin-bottom: 1.5rem
}
</style>