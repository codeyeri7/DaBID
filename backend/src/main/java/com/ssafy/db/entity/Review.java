package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Review {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reviewId;

    @ManyToOne
    @JoinColumn(name="user_id")
    @Column(length = 13)
    private User user;

    @Column(length = 13)
    String reviewWriter;
    @Column(length = 100)
    String reviewContent;
    Timestamp reviewDate;
}