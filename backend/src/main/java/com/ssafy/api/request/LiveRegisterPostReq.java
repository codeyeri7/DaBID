package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * 라이브 생성 API ([POST] /live) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("LiveRegisterPostReq")
public class LiveRegisterPostReq {
//	@ApiModelProperty(name="상품 고유 아이디")
//	int prdId;
	@ApiModelProperty(name="판매자 고유아이디")
	String userId;
	@ApiModelProperty(name="라이브 제목")
	String liveTitle;
	@ApiModelProperty(name="라이브 상세정보")
	String liveDesc;
//	@ApiModelProperty(name="라이브 상태", example = "1")
//	int liveStatus;
	@ApiModelProperty(name="라이브 시작 날짜")
	String liveDate;
	@ApiModelProperty(name="라이브 시작 시간")
	String liveTime;
	@ApiModelProperty(name="상품명")
	String prdName;
	@ApiModelProperty(name="카테고리 번호")
	int prdCategory;
	@ApiModelProperty(name="상품 일련 번호")
	String prdNo;
	@ApiModelProperty(name="상품 사진 S3 url")
	String prdPhoto;
	@ApiModelProperty(name="경매 시작 가격")
	int prdPriceStart;

}
