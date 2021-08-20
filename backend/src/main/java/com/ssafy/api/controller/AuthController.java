package com.ssafy.api.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpTransport;

import com.google.api.client.json.JsonFactory;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.AuthService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;

import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;


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
	AuthService authService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	final String CLIENT_ID = "771637237260-gsvimko90s79m4u8co55gaqaqnplj9ne.apps.googleusercontent.com";

	@PostMapping("/login")
	@ApiOperation(value = "구글 로그인", notes = "회원 관련 정보 저장 & 토큰 발급")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 400, message = "로그인 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<?> login(
			@RequestBody @ApiParam(value="구글 로그인 id_token", required = true) Map <String,String> map)

			throws GeneralSecurityException, IOException {

		String idTokenString = map.get("id_token");
		HttpTransport transport = Utils.getDefaultTransport();
		JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID))
				.build();

		GoogleIdToken idToken = verifier.verify(idTokenString);
		if (idToken != null) {
			// 사용자가 구글 로그인 성공후 ID 토큰
			Payload payload = idToken.getPayload();

			// Print user identifier
			String userId = payload.getSubject();

			// 유저 프로필 정보
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			// email로 이미 있는 유저인지 체크
			String authUserId = authService.getUserIdByUserEmail(email);
			if (authUserId == null) {
				// 신규 회원이라면 User, Auth 테이블에 insert
				User user = userService.createUser(name);
				authService.createUser(user, email);	// Auth 테이블에 insert
				authUserId = user.getUserId();


			}

			// 유저 fullname과 JwtToken 반환
			return ResponseEntity.ok(UserLoginPostRes.of(authUserId, name, JwtTokenUtil.getToken(authUserId)));

		} else {
			// Invalid ID token
			return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Invalid ID token"));
		}
	}

}
