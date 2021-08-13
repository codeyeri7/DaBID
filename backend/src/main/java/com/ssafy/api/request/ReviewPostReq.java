package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 리뷰 작성 API ([POST] /dabid/users/writeReview) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ReviewPostReq")
public class ReviewPostReq {

    @ApiModelProperty(name="유저 id", example="P1628163711395(dabid ID)")
    String userId;
    @ApiModelProperty(name="리뷰 평점", example="정수(- or +)값으로 기존 점수에 합산")
    int addScore;
    @ApiModelProperty(name="리뷰 남길 라이브 prdId", example="리뷰 남길 라이브의 prdId ( int형 )")
    int prdId;
    @ApiModelProperty(name="리뷰 작성 내용", example="리뷰 작성 내용")
    String content;
}
