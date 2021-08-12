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

import com.ssafy.db.repository.ChatRoomRepository;
import com.ssafy.db.vo.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}