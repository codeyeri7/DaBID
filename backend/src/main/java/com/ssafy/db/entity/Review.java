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
    User user;

    String reviewWriter;
    String reviewContent;
    Timestamp reviewDate;
}