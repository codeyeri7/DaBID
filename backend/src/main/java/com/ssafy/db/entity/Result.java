package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Result {

    @Id
    @Column(name="prd_id")
    int prdId;
    int resPriceEnd;    // 경매 마감 가격

    @MapsId //식별관계 + 키 설정
    @OneToOne
    @JoinColumn(name = "prd_id")
    Live live;

    @OneToOne
    @JoinColumn(name="res_buyer_id")
    User user;
    
    @OneToOne(mappedBy = "result")
    private ChatRoom chatRoom;
}
