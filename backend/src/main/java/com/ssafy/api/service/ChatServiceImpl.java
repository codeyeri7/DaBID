//package com.ssafy.api.service;
//
//import com.ssafy.db.entity.Chat;
//import com.ssafy.db.repository.ChatRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//
//public class ChatServiceImpl implements ChatService {
//    private final ChatService chatService;
//
//    @Override
//    public int insertMessage(Chat chatMessage) {
//        return chatService.insertMessage(chatMessage);
//    }
//
//    @Override
//    public List<Chat> getMessagesByChatroomId(int id, int idx) {
//        return chatService.getMessagesByChatroomId(id, idx);
//    }
//}