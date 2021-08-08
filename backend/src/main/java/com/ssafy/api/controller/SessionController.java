package com.ssafy.api.controller;

import com.ssafy.api.response.LiveRes;
import com.ssafy.api.service.LiveService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import io.openvidu.java.client.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 라이브 세션 관련 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value="Live Session API", tags={"Live Session"})
@RestController
@RequestMapping("/dabid")
public class SessionController {

	@Autowired
	UserService userService;

	@Autowired
	LiveService liveService;

	/**
	 * 라이브 세션 참여/떠나기 관련 Controller
	 */
	private OpenVidu openVidu;

	// <세션이름(라이브 제목), OpenVidu Session 객체>
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();

	// <세션이름(라이브 제목), 토큰>
	// 토큰: <토큰, OenViduRole>
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

	// OpenVidu 서버가 수신 중인 URL
	private String OPENVIDU_URL;
	// OpenVidu 서버와 공유되는 SECRET
	private String SECRET;

	public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		System.out.println("ddd"+secret+" "+openviduUrl);
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}

	@PostMapping("/session/{prdId}/{userId}")
	@ApiOperation(value = "세션 생성 혹은 참가",
			notes = "상품고유아이디(prdId)를 파라미터로 받아 통해 라이브 테이블 생성, 세션 생성 혹은 참가.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "에러 발생"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> joinSession(@PathVariable @ApiParam(name="prdId") int prdId,
							  @PathVariable String userId) {

		// 여기부터
		User user = userService.getUserByUserId(userId);	// userId로 user 검색
		String userName = user.getUserName();
		//String liveTitle = "testLive";
//		OpenViduRole role;
//		if (userId.equals("test123")) {
//			role = OpenViduRole.SUBSCRIBER;
//		} else {
//			role = OpenViduRole.PUBLISHER;
//		}
		// 여기까지 테스트를 위한 코드

//		// 1. Authentication에서 가져오기
//		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//		// 2. Authentication에서 얻은 정보에서 userId(로그인한 user의 고유아이디)
//		String userId = userDetails.getUsername();
//		// 3. 얻은 userId로 사용자 정보(User객체) 찾기
//		User user = userService.getUserByUserId(userId);
//		// 4. user에서 닉네임(사용자 이름) 얻어오기
//		String userName  = user.getUserName();
////		System.out.println("Getting sessionId and token | {sessionName}={" + sessionName + "}");
//
		// 파라미터로 넘어온 prdId(상품 고유 아이디)로 해당되는 Live 객체 찾기
		Live live =  liveService.getLiveByPrdId(prdId);
		// live테이블에서 라이브 제목 얻기
		String liveTitle = live.getLiveTitle();

		// 세션 참가자 역할 (PUBLISHER or SUBSCRIBER)
		OpenViduRole role;
		String userRole;
		// 라이브 테이블의 판매자 고유 아이디 조회
		if (userId.equals(live.getUser().getUserId())) {
			// 판매자일 경우
			role = OpenViduRole.PUBLISHER;
			userRole = "PUBLISHER";

		} else {
			// 구매자일 경우
			role = OpenViduRole.SUBSCRIBER;
			userRole = "SUBSCRIBER";
		}

		// 화상미팅에 연결할 때 다른 유저들에게 전송되는 데이터 (optional) - JSON 형식
		String serverData = "{\"serverData\": \"" + userId + "\"}";

		// connectionProperties 객체 빌드 (serverData와 role 필요)
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.role(role).data(serverData).build();

		if (this.mapSessions.get(liveTitle) != null) {
			// 세션이 이미 있는 경우
//			System.out.println("Existing session " + user);
			try {
				// 위에서 생성한 connectionProperties로 새로운 토큰 생성
				String token = this.mapSessions.get(liveTitle).createConnection(connectionProperties).getToken();

				// 생성된 토큰으로 업데이트
				this.mapSessionNamesTokens.get(liveTitle).put(token, role);

				// LiveRes에 정보들 입력하기(라이브 제목, 토큰, 사용자이름, 사용자 아이디)
				return ResponseEntity.status(200).body(LiveRes.of(liveTitle, token, userName, userId, userRole));

			} catch (Exception e) {
				this.mapSessions.remove(liveTitle);
				return ResponseEntity.status(200).body(BaseResponseBody.of(400, "세션은 있고 오류 발생"));
			}
		} else {
			// 새로운 세션
//			System.out.println("New session " + liveTitle);
			try {
				// OpenVidu 세션 생성
				System.out.println(liveTitle+" "+userName+" "+userId);
				Session session = this.openVidu.createSession();

				// 위에서 생성한 connectionProperties로 새로운 토큰 생성
				String token = session.createConnection(connectionProperties).getToken();

				// collections에 저장
				this.mapSessions.put(liveTitle, session);
				this.mapSessionNamesTokens.put(liveTitle, new ConcurrentHashMap<>());
				this.mapSessionNamesTokens.get(liveTitle).put(token, role);

				// LiveRes에 정보들 입력하기(라이브 제목, 토큰, 사용자이름, 사용자 아이디)
				return ResponseEntity.status(200).body(LiveRes.of(liveTitle, token, userName, userId, userRole));

			} catch (Exception e) {
				System.out.println(e.getMessage());
				return ResponseEntity.status(200).body(BaseResponseBody.of(400, "세션 없고 오류 발생"));
			}
		}
	}

	@PostMapping("/leave-session")
	@ApiOperation(value = "세션 떠나기", notes = "세션 떠나기.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "the SESSION does not exist"),
			@ApiResponse(code = 403, message = "the TOKEN wasn't valid"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> leaveSession(
			@RequestBody @ApiParam(name="토큰 & 라이브 제목") Map<String, String> reqInfo,
			@ApiIgnore Authentication authentication) throws Exception {
		System.out.println("hhhh");
//		System.out.println("Removing user | sessioName=" + sessionName + ", token=" + token);

		String token = reqInfo.get("token");
		String liveTitle = reqInfo.get("liveTitle");

		// 세션이 존재한다면
		if (this.mapSessions.get(liveTitle) != null && this.mapSessionNamesTokens.get(liveTitle) != null) {

			// 토큰이 존재한다면
			if (this.mapSessionNamesTokens.get(liveTitle).remove(token) != null) {
				// 유저가 세션을 떠남
				if (this.mapSessionNamesTokens.get(liveTitle).isEmpty()) {
					// 마지막 유저가 세션을 떠남: 세션은 삭제
					this.mapSessions.remove(liveTitle);
				}
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "세션 나가기 성공"));

			} else {
				// 토큰이 유효하지 않음
				return ResponseEntity.status(403).body(BaseResponseBody.of(403, "the TOKEN wasn't valid"));
			}

		} else {
			// 세션이 존재하지 않음
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "the SESSION does not exist"));
		}
	}

//	private void checkUserLogged(HttpSession httpSession) throws Exception {
//		if (httpSession == null || httpSession.getAttribute("loggedUser") == null) {
//			throw new Exception("User not logged");
//		}
//	}

}//
