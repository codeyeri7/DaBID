package com.ssafy.db.repository;

import com.ssafy.db.vo.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

// import 생략....

@Repository
public class ChatRoomRepository{

    private Map<Integer, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public void deleteChatRoom(String id){
        chatRoomMap.remove(id);
    }

    public ChatRoom createChatRoom(int prdId) {
        ChatRoom chatRoom = ChatRoom.create(prdId);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}