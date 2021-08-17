package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogPostReq {

    private int prdId;
    private int bidPrice;
    private String bidder;
}