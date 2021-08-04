package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private int chatRoomId;

    @Column(name="prd_id",insertable=false, updatable=false)
    private int prdId;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Chat> chatlist = new ArrayList<>();
    //many를 담아주는 객체라서 List

    @OneToOne
    @JoinColumn(name="prd_id")
    private Result result;
}
