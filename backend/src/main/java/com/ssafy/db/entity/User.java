package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User {
    @Id
    @Column(name="user_id")
    String userId;      // 회원 고유 아이디
    String userName;    // 이름(닉네임)
    String userScore;   // 평점

    @OneToOne(mappedBy = "user")
    Auth auth;

    @OneToOne(mappedBy = "user")
    Result result;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Review> logs = new ArrayList<>();

    @OneToMany(mappedBy = "prdSellerId")
    private List<Live> liveList = new ArrayList<>();
}