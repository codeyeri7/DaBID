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
    @GenericGenerator(name = "UserIdGenerator", // @GeneratedValue의 generator modifier에서 사용할 이름
            strategy = "com.ssafy.db.generator.UserIdGenerator") // IdentifierGenerator 인터페이스를 구현한 클래스 이름
    @GeneratedValue(generator = "UserIdGenerator")  // @GenericGenerator의 name modifier 에 지정한 이름
    private String userId;      // 회원 고유 아이디
    private  String userEmail;   // 구글 이메일

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private  User user;

}
