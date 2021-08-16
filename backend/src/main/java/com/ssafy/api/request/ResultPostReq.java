package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 라이브 결과 생성 API ([POST] /dabid/live/end/{prdId}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ResultPostReq")
public class ResultPostReq {
    @ApiModelProperty(name="판매자 유저 id")
    String sellerId;

    @ApiModelProperty(name="구매자 유저 id")
    String buyerId;

    @ApiModelProperty(name="경매 마감 가격")
    int resPriceEnd;
}
