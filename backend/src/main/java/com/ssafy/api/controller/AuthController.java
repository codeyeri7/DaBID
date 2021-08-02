package com.ssafy.api.controller;

import com.ssafy.api.service.OauthService;
import com.ssafy.api.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	OauthService oauthService;

	/**
	 * 사용자로부터 SNS 로그인 요청을 받아 처리
	 */
	@GetMapping()
	public void socialLoginType() {
		oauthService.request();
	}

	/**
	 * Social Login API Server 요청에 의한 callback 을 처리
	 * @param code API Server 로부터 넘어오는 code
	 * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
	 */
	@GetMapping(value = "/callback")
	public String callback(
			@RequestParam(name = "code") String code) {

		String accessToken = oauthService.requestAccessToken(code);
//		if (accessToken.equals(null)){
//			return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "구글 로그인 요청 처리 실패", null));
//		} else {
//			// { "access_token": "ya29.a0ARrdaM-9MI05d738WR3kJ8P_4yDiuLycf70p49OcFDieWAT5ePvI8XpO-vXMm1yi5RBAKIwu0fSRboXoRkcrIbLVdGHXwspizjB0cRuTLBkr6mEL6DeamxtmHENrqyp5BfmIXjidzbP9yghaymFMhi0HQSIQ", "expires_in": 3581, "scope": "https://www.googleapis.com/auth/userinfo.profile", "token_type": "Bearer", "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjBmY2MwMTRmMjI5MzRlNDc0ODBkYWYxMDdhMzQwYzIyYmQyNjJiNmMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI3NzE2MzcyMzcyNjAtZ3N2aW1rbzkwczc5bTR1OGNvNTVnYXFhcW5wbGo5bmUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI3NzE2MzcyMzcyNjAtZ3N2aW1rbzkwczc5bTR1OGNvNTVnYXFhcW5wbGo5bmUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTQ2MDIzMzY3ODUzMjEyNzE2ODUiLCJhdF9oYXNoIjoiMTY0YUhzNUJDNnhQZGU2VU1yanNLZyIsIm5hbWUiOiLrionrg6Xrh70iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2pxZTFxM2s2YnRBMC15WEZvT2VjY29MVFR4WG14Z1VraUhia051dlE9czk2LWMiLCJnaXZlbl9uYW1lIjoi64Ol64e9IiwiZmFtaWx5X25hbWUiOiLriokiLCJsb2NhbGUiOiJrbyIsImlhdCI6MTYyNzg3MTMyNywiZXhwIjoxNjI3ODc0OTI3fQ.4tk2GkV2ulRqfHQeBmnsUu79qrGQ3nQah5AlWepMdMltg7pHPFz0xCZDLfJuoCNN-AcBeAV7YI8_ooHwkMirYb-j2oQBZf76syi5IBB-XI9qcgwQxXxXuv-y17pChLO9WUGRQpW1JGuVJpLIkGmWwGQfxXiV1S5d3svuiIauP8UdLIZG6jjqVBluE5aQnxwSyRGZj_K_MX0QHUchzUlIUJrZYJOQOwnD8Fxij7jisTOWDAtboayl8DBZEWR0z5hW0PwM1zvgBcOvRdWLi7d-jCmOeEn_1dhChmzFQWtv_3R2sKLCMOhIT7eQG6LFTxj4GvUal1aTPGWBgOF82NcCJw" }
//			// yjanmin
//			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(accessToken)));
//		}
		return accessToken;
	}

//	@PostMapping("/login")
//	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
//        @ApiResponse(code = 401, message = "잘못된 비밀번호입니다.", response = BaseResponseBody.class),
//        @ApiResponse(code = 404, message = "존재하지 않는 계정입니다.", response = BaseResponseBody.class),
//        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
//    })
//	public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
//		String userId = loginInfo.getId();
//		String password = loginInfo.getPassword();
//
//		User user = userService.getUserByUserId(userId);
//		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
//		if(passwordEncoder.matches(password, user.getPassword())) {
//			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
//			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
//		}
//		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
//		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
//	}


}