package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private User user;

    @Column(length = 14)
    String reviewWriter;
    @Column(length = 100)
    String reviewContent;
    @Column(name = "reviewDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Timestamp reviewDate;
}