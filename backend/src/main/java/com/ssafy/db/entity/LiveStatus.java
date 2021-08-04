package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class LiveStatus { //라이브 상태 테이블

    @Id
    @ColumnDefault("0")
    int liveStatus; //라이브 상태
    @Column(length = 20)
    String liveStatusName; //라이브 상태 이름

    //mappedBy - 자신을 참조하고 있는 애들
    @OneToOne(mappedBy = "liveStatus", cascade = CascadeType.PERSIST) //Live테이블과 1:1관계
    private Live live;

}
