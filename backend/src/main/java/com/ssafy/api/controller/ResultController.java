package com.ssafy.api.controller;

import com.ssafy.api.service.ResultService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 채팅 관련 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value="Chat API", tags={"Chat"})
@RestController
@RequestMapping("/dabid/chat")
public class ResultController {
    @Autowired
    ResultService resultService;


    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ApiOperation(value = "전체 채팅방 조회", notes = "전체 채팅 조회")
    public ResponseEntity<?> room(@ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        List<Result> chatRoom = resultService.findAllResult(userId);
        return ResponseEntity.status(200).body(chatRoom);
    }

    // 채팅방 생성
//    @PostMapping("/room/{prdId}")
//    @ApiOperation(value = "채팅방 생성", notes = "채팅방 생성.")
////    public ChatRoomPostReq createRoom(@PathVariable String roomName,
//    public ResponseEntity<?> createRoom(@PathVariable int prdId,
//                                      @ApiIgnore Authentication authentication) {
//        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//        String userId = userDetails.getUsername();
//
////        chatRoomService.createChatRoom(prdId);
//
//        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"채팅방이 생성완료."));
//    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ApiOperation(value = "특정 채팅방 조회", notes = "특정 채팅방 조회.")
//    public ResponseEntity<?> roomInfo(@PathVariable String roomId) {
    public ResponseEntity<?> roomInfo(@PathVariable int prdId) {
        Result result = resultService.findResultById(prdId);
        if(result.equals(null))
            return ResponseEntity.status(200).body(BaseResponseBody.of(400,"채팅방이 존재하지 않습니다."));
        return ResponseEntity.status(200).body(result);
    }

    // 특정 채팅방 삭제
//    @DeleteMapping("/room/{roomId}")
//    @ApiOperation(value = "특정 채팅방 삭제", notes = "특정 채팅방 삭제.")
//    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
//        chatRoomService.deleteChatRoom(roomId);
//        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "채팅방이 삭제되었습니다."));
//    }
}