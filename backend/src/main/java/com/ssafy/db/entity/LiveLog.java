package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LiveLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    @ManyToOne()
    @JoinColumn(name = "prd_id")
    @JsonIgnore
    private Live live;

    private int bidPrice;
    private String bidder;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp bidTime;
}