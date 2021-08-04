package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Result {

    @Id
    @Column(name="prd_id")
    private int prdId;
    private int resPriceEnd;    // 경매 마감 가격

    @Column(name="res_buyer_id", insertable=false, updatable=false)
    private String resBuyerId;

    @MapsId //식별관계 + 키 설정
    @OneToOne
    @JoinColumn(name = "prd_id")
    private Live live;

    @OneToOne
    @JoinColumn(name="res_buyer_id")
    private User user;
    
    @OneToOne(mappedBy = "result")
    private ChatRoom chatRoom;
}
