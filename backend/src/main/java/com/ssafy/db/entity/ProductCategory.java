package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class ProductCategory { //상품카테고리

    @Id
    int prdCategory; //상품 카테고리 번호
    String prdCategoryName; //상품 카테고리 이름

    @OneToOne(mappedBy = "productCategory") //Live테이블과 1:1관계
    private Live live;
}