package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Session {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 13)
    int sessionId;
    @Column(length = 45)
    String sessionToken;
    String userId;
    Date sessionDate;
    @Column(length = 45)
    String sessionStatus;
}
//쓸거?