package com.ssafy.db.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuthId implements Serializable {
    private String userId;  // @MapsId("userId") 매핑
}
