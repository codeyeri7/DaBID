package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 채팅방
 */
@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int chatroomId;

    @OneToMany(mappedBy = "chatRoom")
    List<Chat> chatlist = new ArrayList<>();
    //many를 담아주는 객체라서 List

    @OneToOne
    @JoinColumn(name="prd_id")
    private Result result;
}
