//package com.ssafy.api.service;
//
//import com.ssafy.db.entity.ChatRoom;
//import com.ssafy.db.entity.Result;
//import com.ssafy.db.repository.ChatRoomRepository;
//import com.ssafy.db.repository.ResultRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class ChatRoomServiceImpl implements ChatRoomService {
//    @Autowired
//    private final ChatRoomRepository chatRoomRepository;
//    @Autowired
//    private final ResultRepository resultRepository;
//
//    @Override
//    public void createRoom(int prdId) {
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setResult(resultRepository.findByPrdId(prdId).orElseGet(null));
//
//        chatRoomRepository.save(chatRoom);
//
////        int result = chatRoomRepository.createRoom(chatRoom);
////        return result == 1 ? chatRoom.getChatRoomId() : -1;
//    }
//
//    @Override
//    public List<ChatRoom> getAllChatRooms(String userId) {
//        return chatRoomRepository.getAllChatRooms(userId);
//
//    }
//
//    @Override
//    public String getRoomTitle(int id) {
//        return chatRoomRepository.getRoomTitle(id);
//    }
//}