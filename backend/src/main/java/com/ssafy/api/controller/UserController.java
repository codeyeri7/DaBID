package com.ssafy.api.controller;

import com.ssafy.api.request.LiveRegisterPostReq;
import com.ssafy.api.request.ReviewPostReq;
import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.LiveService;
import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/dabid/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/myLive")
	@ApiOperation(value = "마이 라이브 조회", notes = "내가 등록한 라이브 전체 조회")
	public ResponseEntity<?> selectMyLives(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();

		List<Live> myLiveList = userService.getMyLives(userId);
		return ResponseEntity.status(200).body(myLiveList);
	}

	@GetMapping("/getLive/{userId}")
	@ApiOperation(value = "선택한 유저의 라이브 조회", notes = "선택한 유저가 등록한 라이브를 조회할 수 있다.")
	public ResponseEntity<?> getUserLives(@PathVariable("userId") String userId) {
		List<Live> liveList = userService.getMyLives(userId);
		return ResponseEntity.status(200).body(liveList);
	}

	@PatchMapping()
	@ApiOperation(value = "회원 수정", notes = "사용자의 <strong>이름</strong>을 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> updateNickName(
			@RequestBody @ApiParam(value="회원정보 수정", required = true) Map<String, String> map,
			@ApiIgnore Authentication authentication) {

		String userName = map.get("userName");
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();

		userService.updateUser(userId, userName);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@DeleteMapping("/{userId}")
	@ApiOperation(value = "회원 삭제", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> delete(
			@PathVariable("userId") String userId) {

		userService.deleteUser(userId);

		return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
	}

	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
	public ResponseEntity<?> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		System.out.println(authentication);
		System.out.println(authentication.getDetails());
		System.out.println(authentication.isAuthenticated());

		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		return ResponseEntity.status(200).body(user);
	}

	@GetMapping("/{userId}")
	@ApiOperation(value = "선택한 회원 정보 조회", notes = "선택한 회원의 유저정보와 등록한 라이브 정보 조회")
	public ResponseEntity<?> getAnotherUserInfo(@PathVariable("userId") String userId) {

		User user = userService.getUserByUserId(userId);
		List<Live> liveList = userService.getMyLives(userId);

		UserRes userRes = new UserRes(user,liveList);

		return ResponseEntity.status(200).body(userRes);
	}



	//=============================== 리뷰 관련 ===============================

	@PostMapping("/writeReview")
	@ApiOperation(value = "리뷰 작성", notes = "선택한 라이브 방송 리뷰 남기기")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "에러 발생"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> createReview(
			 @RequestBody @ApiParam(value="작성자, 리뷰 남길 라이브 prdId, 평점", required = true) ReviewPostReq reviewPostReq){

		int prdId = reviewPostReq.getPrdId(); //리뷰 남길 라이브
		String reviewWriter = reviewPostReq.getUserId(); //작성자
		int addScore = reviewPostReq.getAddScore(); //리뷰 평점
		String content = reviewPostReq.getContent(); //리뷰 내용
		System.out.println("ddd"+prdId+" "+reviewWriter+" "+addScore+" "+content);

		try {
			userService.writeReview(prdId, reviewWriter, addScore, content);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}catch (Exception e){
			e.printStackTrace();
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(400, "리뷰 작성에 실패했습니다."));
	}

	@GetMapping("/checkReview/{userId}")
	@ApiOperation(value = "해당회원에게 쓴 리뷰확인", notes = "파라미터로 userId를 받아 해당 회원에게 쓴 리뷰 확인")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "해당 user에게 남긴 리뷰가 없습니다."),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> checkReview(@PathVariable("userId") String userId){
		List<Review> reviewList = userService.checkReview(userId);

		if(reviewList.size()==0) return ResponseEntity.status(409).body(null);
		else return ResponseEntity.status(200).body(reviewList);
	}



}
