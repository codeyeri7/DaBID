package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Result {

    @Id
    @Column(name="prd_id")
    private int prdId;
    private int resPriceEnd;    // 경매 마감 가격

    @MapsId //식별관계 + 키 설정
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prd_id")
    private Live live;

    @OneToOne
    @JoinColumn(name="res_buyer_id")
    private User buyer;

    @OneToOne
    @JoinColumn(name="res_seller_id")
    private User seller;

    @OneToMany(mappedBy = "result", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Chat> chatlist = new ArrayList<>();
}