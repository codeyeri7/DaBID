package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원수정 API ([PATCH] /api/v1/users/userid) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdatePatchRequest")
public class UserUpdatePatchReq {
//	@ApiModelProperty(name="유저 ID", example="your_id")
//	String id;
	@ApiModelProperty(name="유저 department", example="your_department")
	String department;
	@ApiModelProperty(name="유저 name", example="심재원")
	String name;
	@ApiModelProperty(name="유저 position", example="your_position")
	String position;
}
