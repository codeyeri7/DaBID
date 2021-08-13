package com.ssafy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.db.vo.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

// import 생략...

@RequiredArgsConstructor
@Controller
//@RestController
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message/")
    public void message(ChatMessage message) {
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())){
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        }
//        System.out.println(message.getMessage());
        messagingTemplate.convertAndSend("/sub/chat/room/", message);
    }
}

















