package com.ssafy.api.service;

import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.AuthRepository;
import com.ssafy.db.repository.LiveRepository;
import com.ssafy.db.repository.ReviewRepository;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	LiveRepository liveRepository;

	@Override
	public List<Live> getMyLives(String userId) {
		User user = getUserByUserId(userId);
		return user.getLiveList();
	}

	@Override
	public User createUser(String userName) {
		User user = new User();
		user.setUserName(userName);

		return userRepository.save(user);
	}

	@Override
	public User updateUser(String userId, String userName) {
		User user = getUserByUserId(userId);
		user.setUserName(userName);
		return userRepository.save(user);
	}
	@Override
	public void deleteUser(String userId) {
//		Long id = getUserByUserId(userId).getId();
//		userRepository.deleteById(id);
	}

	@Override
	public boolean checkUser(String userId){
//		Long id = getUserByUserId(userId).getId();
//		if(userRepository.findById(id).equals(null)){
//			return false;
//		}
		return true;
	}

	@Override
	public List<Review> checkReview(String userId) {
		User user = getUserByUserId(userId);
		List<Review> reviewList = reviewRepository.findByUser(user).orElse(null);
		return reviewList;
	}

	@Override
	public void writeReview(int prdId, String reviewWriter, int addScore, String content){
		Live live = liveRepository.findByPrdId(prdId).orElse(null);
		User user = live.getUser();

		//리뷰작성
		Review review = new Review();
		review.setUser(user);
		review.setReviewWriter(reviewWriter);
		review.setReviewContent(content);
		reviewRepository.save(review);

		//평점 변경
		user.setUserScore(user.getUserScore()+addScore);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		//User user = userRepositorySupport.findUserByUserId(userId).get();
//		User user = userRepository.findByUserId(userId).get();
		User user = userRepository.findByUserId(userId).orElse(null);
		return user;
	}

}
