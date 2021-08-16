package com.ssafy.api.controller;
//
//import com.ssafy.api.service.ChatRoomService;
//import com.ssafy.api.service.ChatService;
//import com.ssafy.db.entity.Chat;
//import com.ssafy.db.entity.ChatRoom;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*")
//@Slf4j
//@RequestMapping("/api/chat")
//@RequiredArgsConstructor
//@RestController
//public class ChatRoomController {
//    private final ChatRoomService chatroomService;
//    private final ChatService messageService;
//    final int PAGE = 10;
//
//    // 특정 채팅방 타이틀 가져오기
//    @GetMapping("/room/{id}")
//    public ResponseEntity<String> roomTitle(@PathVariable int id) {
//        String roomTitle = chatroomService.getRoomTitle(id);
//        if (roomTitle == null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//
//        return ResponseEntity.status(HttpStatus.OK).body(roomTitle);
//    }
//
//    // 모든 채팅방 목록 반환
//    @GetMapping("/rooms")
//    public ResponseEntity<List<ChatRoom>> room() {
//        List<ChatRoom> rooms = chatroomService.getAllChatRooms();
//        if (rooms == null || rooms.size() == 0)
//            return ResponseEntity.status(HttpStatus.OK).body(null);
//        else
//            return ResponseEntity.status(HttpStatus.OK).body(rooms);
//
//    }
//
//    // 방 생성
//    @PostMapping("/room/{prdId}")
//    public ResponseEntity<Integer> createRoom(@PathVariable int prdId) {
//        int resultOfCreation = chatroomService.createRoom(prdId);
//        if (resultOfCreation >= 0)
//            return ResponseEntity.status(HttpStatus.OK).body(resultOfCreation);
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Integer.MIN_VALUE);
//    }
//
//    // 특정 채팅방 의 메세지 최근 10개
//    @GetMapping("/room/message/{id}")
//    public ResponseEntity<List<Chat>> roomInfo(@PathVariable int id,
//                                               @RequestParam(value = "page", defaultValue = "0") String page) {
//        int idx = page.equals("0") ? 0 : Integer.parseInt(page) * PAGE + 1;
//        List<Chat> msgList = messageService.getMessagesByChatroomId(id, idx);
//        return ResponseEntity.status(HttpStatus.OK).body(msgList);
//    }
//}


// import 생략...

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.repository.ChatRoomRepository;
import com.ssafy.db.vo.ChatRoom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 채팅 관련 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value="Chat API", tags={"Chat"})
@RestController
@RequestMapping("/dabid/chat")
public class ChatRoomController {
    @Autowired
    ChatRoomRepository chatRoomRepository;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ApiOperation(value = "전체 채팅 조회", notes = "전체 채팅 조회")
    public ResponseEntity<?> room(){
        List<ChatRoom> chatRoom = chatRoomRepository.findAllRoom();
        return ResponseEntity.status(200).body(chatRoom);
    }

    // 채팅방 생성
    @PostMapping("/room/{prdId}")
    @ApiOperation(value = "채팅방 생성", notes = "채팅방 생성.")
    public ChatRoom createRoom(@PathVariable int prdId) {
        return chatRoomRepository.createChatRoom(prdId);
    }

//    // 채팅방 입장 화면
//    @GetMapping("/room/enter/{roomId}")
//    @ApiOperation(value = "채팅방 입장 화면", notes = "채팅방 입장 화면.")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";
//    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ApiOperation(value = "특정 채팅방 조회", notes = "특정 채팅방 조회.")
    public ResponseEntity<?> roomInfo(@PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findRoomById(roomId);
        if(chatRoom.equals(null))
            return ResponseEntity.status(200).body(BaseResponseBody.of(400,"채팅방이 존재하지 않습니다."));
        return ResponseEntity.status(200).body(chatRoom);
    }

    // 특정 채팅방 삭제
    @DeleteMapping("/room/{roomId}")
    @ApiOperation(value = "특정 채팅방 삭제", notes = "특정 채팅방 삭제.")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        chatRoomRepository.deleteChatRoom(roomId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "채팅방이 삭제되었습니다."));
    }
}