package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@ApiModel("NoticePostReq")
public class NoticePostReq {
    @ApiModelProperty(name="공지사항 제목")
    String noticeTitle;

    @ApiModelProperty(name="작성일시")
    Date noticeDate;

    @ApiModelProperty(name="공지사항 타입")
    Boolean noticeType;

    @ApiModelProperty(name="공지사항 내용")
    String noticeContent;
}
