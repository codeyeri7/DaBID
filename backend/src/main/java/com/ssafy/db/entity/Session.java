package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Session {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sessionId;
    String sessionToken;
    char userId;
    Date sessionDate;
    String sessionStatus;
}