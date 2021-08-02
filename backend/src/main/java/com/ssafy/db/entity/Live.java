package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Live { //라이브테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prd_id")
    int prdId;          // 상품 고유 아이디
    String liveTitle;   // 라이브 제목
    String liveDesc;    // 라이브 상세정보
    Timestamp liveDate; // 라이브 시작 날짜
    String prdName;     // 상품명
    String prdNo;       // 상품 일렬 번호
    String prdPhoto;    // 상품사진
    int prdPriceStart;  // 경매 시작 가격

    @OneToOne
    @JoinColumn(name="live_status")
    private LiveStatus liveStatus;

    @OneToOne(mappedBy = "live")
    private Result result;

    @OneToOne
    @JoinColumn(name="prd_category")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "prd_seller_id") // 외래키
    private User prdSellerId;
}
