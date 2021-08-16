package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.api.response.ReviewRes;
import com.ssafy.db.entity.*;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(String userName);
	User updateUser(String userId, String userName);
	User getUserByUserId(String userId);
	void deleteUser(String userId);
	boolean checkUser(String userId);

	List<ReviewRes> checkReview(String userId);
	void writeReview(int prdId, String reviewWriter, int addScore, String content);

	List<Live> getMyLives(String userId);
}
