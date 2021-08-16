package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name="prd_id")
    private Result result;
}