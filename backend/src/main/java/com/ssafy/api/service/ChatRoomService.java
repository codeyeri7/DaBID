package com.ssafy.api.service;

import com.ssafy.api.request.ChatRoomPostReq;
import com.ssafy.db.entity.ChatRoom;
import com.ssafy.db.entity.Result;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ChatRoomRepository;
import com.ssafy.db.repository.ResultRepository;
import com.ssafy.db.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

// import 생략....

//@Repository
@Service("ChatRoomService")
public class ChatRoomService {

//    private Map<String, ChatRoomPostReq> chatRoomMap;
//
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ResultRepository resultRepository;
    @Autowired
    UserRepository userRepository;

    public List<ChatRoom> findAllRoom(String userId) {
        // 채팅방 생성순서 최근 순으로 반환
        User user = userRepository.findByUserId(userId).orElseGet(null);
        List<Result> resultList = resultRepository.findBySellerOrBuyer(user, user);

        List<ChatRoom> chatRooms = new ArrayList<>();
        for (Result result : resultList) {
            chatRooms.add(chatRoomRepository.findByResult(result));
        }
        return chatRooms;
    }

    public ChatRoom findRoomById(int prdId) {
        Result result = resultRepository.findByPrdId(prdId).orElseGet(null);
        return chatRoomRepository.findByResult(result);
//        return chatRoomMap.get(id);
    }

//    public void deleteChatRoom(String id){
//        chatRoomMap.remove(id);
//    }
//
//    public ChatRoomPostReq createChatRoom(String name) {
//        ChatRoomPostReq chatRoom = ChatRoomPostReq.create(name);
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }
    public void createChatRoom(String name) {
        // 여기 바꿀 차례~
        // ChatRoom 객체 만들어서 받아온 prdId로 이거저거 셋팅하고
        // 저장하기~
        // 채팅방 아이디도 필요한데.. 얘는 autoincrement 아닌가?
//        ChatRoom chatRoom = ChatRoomPostReq.create(name);
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setChatRoomId(prdId);
//        chatRoom.setResult(resultRepository.findByPrdId(prdId));
        chatRoomRepository.save(chatRoom);
//        return chatRoom;
    }
}