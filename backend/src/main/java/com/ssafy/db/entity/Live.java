package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Live { //라이브테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prd_id")
    int prdId;          // 상품 고유 아이디

    @Column(length = 50)
    String liveTitle;   // 라이브 제목
    @Column(length = 100)
    String liveDesc;    // 라이브 상세정보
    Timestamp liveDate; //라이브 시작 날짜
    @Column(length = 50)
    String prdName;     // 상품명
    @Column(length = 100)
    String prdNo;       // 상품 일렬 번호
    @Column(length = 200)
    String prdPhoto;    // 상품사진
    int prdPriceStart;  // 경매 시작 가격

//    @Column(name="prd_seller_id",insertable=false, updatable=false)
//    String prdSellerId;
//    @Column(name="prd_category",insertable=false, updatable=false)
//    int prdCategory;

    @ManyToOne()
    @JoinColumn(name="live_status")
    private LiveStatus liveStatus;

    @OneToOne(mappedBy = "live")
    private Result result;

    @ManyToOne()
    @JoinColumn(name="prd_category")
    private ProductCategory productCategory;

    @ManyToOne()
    @JoinColumn(name = "prd_seller_id") // 외래키
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "live", orphanRemoval = true)
    private List<WishList> WishList = new ArrayList<>();
}
