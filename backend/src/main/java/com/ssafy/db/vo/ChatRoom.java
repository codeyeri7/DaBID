package com.ssafy.db.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    private int roomId;

    public static ChatRoom create(int prdId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = prdId;
        return chatRoom;
    }
}