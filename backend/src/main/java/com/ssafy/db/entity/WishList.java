package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WishList { //찜하기 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wish_id")
    int wishId;

    @ManyToOne()
    @JoinColumn(name = "user_id") // 외래키
    private User user;

    @ManyToOne()
    @JoinColumn(name = "prd_id") // 외래키
    private Live live;

}
