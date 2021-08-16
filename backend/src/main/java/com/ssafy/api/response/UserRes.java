package com.ssafy.api.response;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	User user;
	List<Live> liveList;

	public UserRes(User user, List<Live> liveList) {
		this.user = user;
		this.liveList = liveList;
	}
}
