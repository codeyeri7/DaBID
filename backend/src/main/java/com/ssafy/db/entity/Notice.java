package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date noticeDate;
    private Boolean noticeType;
    @Column(length = 200)
    private String noticeContent;
}