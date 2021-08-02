package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class LiveStatus { //라이브 상태 테이블

    @Id
    int liveStatus; //라이브 상태
    String liveStatusName; //라이브 상태 이름

    //mappedBy - 자신을 참조하고 있는 애들
    @OneToOne(mappedBy = "liveStatus") //Live테이블과 1:1관계
    private Live live;

}
