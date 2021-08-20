package com.ssafy.api.controller;

import com.ssafy.api.response.LiveRes;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.WishService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "찜하기 API", tags = {"WishList"})
@RestController
@RequestMapping("/dabid/wish")
public class WishController {
	
	@Autowired
	WishService wishService;
	@Autowired
	UserService userService;

	@GetMapping("/wishLive")
	@ApiOperation(value = "찜하기 목록 라이브 조회", notes = "내가 찜한 라이브 전체 조회")
	public ResponseEntity<?> selectWishLive(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		List<WishList> wishLiveList = wishService.getWishLives(user);
		return ResponseEntity.status(200).body(wishLiveList);
	}

	@GetMapping("/check/{prdId}")
	@ApiOperation(value = "내가 찜한 라이브인지 판별", notes = "로그인한 유저가 찜한 라이브인지 아닌지 판별")
	public ResponseEntity<?> checkWishLive(@PathVariable int prdId,@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);
		Boolean flg = false;
		flg = wishService.checkWishLive(user,prdId);
		return ResponseEntity.status(200).body(flg);
	}

	@PostMapping("/{prdId}")
	@ApiOperation(value = "찜하기 목록에 추가", notes = "마이페이지에서 WishList에 찜한 방송으로 추가")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "에러 발생"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> pushWishLive(@PathVariable int prdId, @ApiIgnore Authentication authentication) {
		try {
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			String userId = userDetails.getUsername();
			User user = userService.getUserByUserId(userId);

			wishService.putWishLive(user, prdId);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}catch (Exception e){
			e.printStackTrace();
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(400, "찜하기 추가에 실패했습니다."));
	}

	@DeleteMapping("/{prdId}")
	@ApiOperation(value = "찜하기 목록에서 라이브 삭제", notes = "상품 고유 아이디와 유저아이디를 받아 찜하기 목록에 있는 라이브 삭제한다.")
	public ResponseEntity<? extends BaseResponseBody> deleteWishLive(
			@PathVariable int prdId, @ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		wishService.deleteWishLive(user,prdId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "삭제 성공"));
	}

	@GetMapping("/best")
	@ApiOperation(value = "찜한 하트 수 가장 많은 라이브 top2 조회", notes = "찜한 하트 수 가장 많은 라이브 top2 조회")
	public ResponseEntity<?> bestLives() {
		List<LiveRes> liveList = wishService.getBestLives(); //방송예정 중 인기방송 2개만
		return ResponseEntity.status(200).body(liveList);
	}

}
