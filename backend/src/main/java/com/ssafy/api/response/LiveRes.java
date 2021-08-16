package com.ssafy.api.response;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 라이브 세션 생성 or 참가 API ([POST] /live/{prdId}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("LiveResponse")
public class LiveRes {
	User user;
	Live live;
	int hearts;

	public LiveRes(User user, Live live, int hearts) {
		this.user = user;
		this.live = live;
		this.hearts = hearts;
	}
}
