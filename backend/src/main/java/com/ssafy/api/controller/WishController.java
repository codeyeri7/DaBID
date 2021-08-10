package com.ssafy.api.controller;

import com.ssafy.api.service.WishService;
import com.ssafy.db.entity.WishList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "찜하 API", tags = {"WishList"})
@RestController
@RequestMapping("/dabid/wish")
public class WishController {
	
	@Autowired
	WishService wishService;

	@GetMapping("/{userId}/wishLive")
	@ApiOperation(value = "찜하기 목록 라이브 조회", notes = "내가 찜한 라이브 전체 조회")
	public ResponseEntity<?> selectWishLives(@PathVariable String userId) {
		List<WishList> wishLiveList = wishService.getWishLives(userId);
		return ResponseEntity.status(200).body(wishLiveList);
	}

	@PutMapping("/{prdId}/{userId}")
	@ApiOperation(value = "찜하기 목록에 추가", notes = "마이페이지에서 WishList에 찜한 방송으로 추가")
	public ResponseEntity<?> pushWishLives(@PathVariable int prdId,@PathVariable String userId) {

		WishList wishList = wishService.putWishList(userId, prdId);
		return ResponseEntity.status(200).body(wishList);
	}

}
