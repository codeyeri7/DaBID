package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 라이브 세션 생성 or 참가 API ([POST] /live/{prdId}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("LiveResponse")
public class LiveRes {
	@ApiModelProperty(name="Product ID")
	String liveTitle;	// 라이브 제목
	String token;
	String userName;	// 유저 닉네임
	String userId;		// 회원 고유 아이디
	
	public static LiveRes of(String liveTitle, String token, String userName, String userId) {
		LiveRes res = new LiveRes();
		res.setLiveTitle(liveTitle);
		res.setToken(token);
		res.setUserName(userName);
		res.setUserId(userId);
		return res;
	}
}
