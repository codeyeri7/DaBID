package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ProductCategory { //상품카테고리
    @Id
    int prdCategory; //상품 카테고리 번호

    @Column(length = 20)
    String prdCategoryName; //상품 카테고리 이름

    @JsonIgnore
    @OneToMany(mappedBy = "productCategory") //Live테이블과 1:1관계
    private List<Live> livelist;
}