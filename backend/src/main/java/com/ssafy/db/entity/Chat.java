package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 채팅 기록
 */
@Entity
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;

    private Timestamp chatDate;

    @Column(length = 200)
    private String chatContent;

    private String chatFrom;

    @Column(name = "chat_room_id",insertable=false, updatable=false)
    private int chatRoomId;

    @ManyToOne // 다대일 관계의 연관 관계
    @JoinColumn(name = "chat_room_id") // ChatRoom entity의 PK인 chatroom_id과 참조관계 mapping
    private ChatRoom chatRoom;
}