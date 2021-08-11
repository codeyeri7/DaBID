package com.ssafy.api.controller;

import com.ssafy.api.service.UserService;
import com.ssafy.api.service.WishService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

		System.out.println("USER아이디"+userId);
		List<WishList> wishLiveList = wishService.getWishLives(userId);
		return ResponseEntity.status(200).body(wishLiveList);
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

		wishService.deleteWishLive(userId,prdId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "삭제 성공"));
	}

}
