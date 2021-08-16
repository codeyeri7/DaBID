package com.ssafy.api.controller;

import com.ssafy.api.request.ChatMessage;
import com.ssafy.api.service.LiveService;
import com.ssafy.db.entity.Chat;
import com.ssafy.db.entity.Live;
import com.ssafy.db.repository.ChatRepository;
import com.ssafy.db.repository.LiveRepository;
import com.ssafy.db.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

// import 생략...

@RequiredArgsConstructor
@Controller
//@RestController
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ResultRepository resultRepository;
    LiveRepository liveRepository;

    @Autowired
    LiveService liveService;

    @MessageMapping("/chat/message/")
    public void message(ChatMessage message) {
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())){
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        }
//        System.out.println(message.getMessage());
//        System.out.println(message.getRoomId());
//        Live live = liveRepository.findByPrdId(message.getRoomId()).orElseGet(null);
//        System.out.println("live.getLiveTitle()" + live.getLiveTitle());
//        System.out.println(resultRepository.findByLive(live));

        Chat chat = new Chat();
        chat.setChatContent(message.getMessage());
        chat.setChatFrom(message.getSender());
        Live live = liveService.getLiveByPrdId(message.getRoomId());
        chat.setResult(resultRepository.findByLive(live));
        chatRepository.save(chat);

        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}

















