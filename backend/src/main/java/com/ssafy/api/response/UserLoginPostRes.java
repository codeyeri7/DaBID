package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes {
	String userId;
	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	String accessToken;
	String userName;

	public static UserLoginPostRes of(String userId, String userName, String accessToken) {
		UserLoginPostRes res = new UserLoginPostRes();
		res.setUserId(userId);
		res.setUserName(userName);
		res.setAccessToken(accessToken);
		return res;
	}
}