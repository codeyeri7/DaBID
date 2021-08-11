//package com.ssafy.api.controller;
//
//import com.ssafy.api.service.ChatService;
//import com.ssafy.db.entity.Chat;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin(origins = "*")
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class ChatController {
//    private final ChatService messageService;
//    private final SimpMessagingTemplate template;
//
//    @MessageMapping("/message")
//    public void sendMessage(@Payload Chat chatMessage) {
//        log.info("전달 메세지 : " + chatMessage);
//
//        messageService.insertMessage(chatMessage);
//        template.convertAndSend("/sub/" + chatMessage.getChatRoomId(), chatMessage);
//    }
//}
////    // /receive를 메시지를 받을 endpoint로 설정합니다.
////    @MessageMapping("/receive")
////
////    // /send로 메시지를 반환합니다. /send를 토픽으로 삼는 모든 subscriber에게 return
////    @SendTo("/send")
////
////    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
////    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
////    public SocketVO SocketHandler(SocketVO socketVO) {
////        // vo에서 getter로 userName을 가져옵니다.
////        String userName = socketVO.getUserName();
////        // vo에서 setter로 content를 가져옵니다.
////        String content = socketVO.getContent();
////
////        // 생성자로 메시지의 페이로드(반환값)을 생성합니다.
////        SocketVO result = new SocketVO(userName, content);
////        // 반환
////        return result;
////    }
//
//
////}