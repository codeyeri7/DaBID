package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
    @Column(name="user_id", length=14)
    @GenericGenerator(name = "UserIdGenerator", // @GeneratedValue의 generator modifier에서 사용할 이름
            strategy = "com.ssafy.db.generator.UserIdGenerator") // IdentifierGenerator 인터페이스를 구현한 클래스 이름
    @GeneratedValue(generator = "UserIdGenerator")  // @GenericGenerator의 name modifier 에 지정한 이름
    String userId;      // 회원 고유 아이디
    String userName;    // 이름(닉네임)

    @ColumnDefault("30")
    int userScore;   // 평점

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    Auth auth;

    @JsonIgnore
    @OneToOne(mappedBy = "buyer")
    Result result1;

    @JsonIgnore
    @OneToOne(mappedBy = "seller")
    Result result2;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Live> liveList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<WishList> WishList = new ArrayList<>();
}