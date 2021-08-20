package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Notice {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeId;
    @Column(length = 50)
    private String noticeTitle;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date noticeDate;

    private Boolean noticeType;
    @Column(length = 200)
    private String noticeContent;
}