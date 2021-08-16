package com.ssafy.api.response;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("ReviewResponse")
public class ReviewRes {
	Review review;
	String writerName;
	String userName;

	public ReviewRes(Review review, String writerName, String userName) {
		this.review = review;
		this.writerName = writerName;
		this.userName = userName;
	}
}
