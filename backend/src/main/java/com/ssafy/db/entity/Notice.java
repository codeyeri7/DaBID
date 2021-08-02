package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Notice {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int noticeId;
    String noticeTitle;
    Timestamp noticeDate;
    Boolean noticeType;
    String noticeContent;
}