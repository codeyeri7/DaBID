package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoomPostReq {
    private String roomId;
    private String name;

    public static ChatRoomPostReq create(String name) {
        ChatRoomPostReq chatRoom = new ChatRoomPostReq();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
    }