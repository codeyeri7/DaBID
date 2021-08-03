package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 인증 모델 정의.
 * User table과 1:1 식별 관계
 */
@Entity
@Getter
@Setter
public class Auth {
    @Id
    @Column(name="user_id")
    private String userId;      // 회원 고유 아이디
    private  String userEmail;   // 구글 이메일

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private  User user;

}
