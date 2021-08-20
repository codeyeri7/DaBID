package com.ssafy.api.service;

import com.ssafy.api.response.ReviewRes;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.LiveRepository;
import com.ssafy.db.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.repository.UserRepository;

import java.util.ArrayList;
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
	public List<ReviewRes> checkReview(String userId) {
		User user = getUserByUserId(userId);
		List<Review> reviewList = reviewRepository.findByUser(user).orElse(null);
		List<ReviewRes> resList = new ArrayList<>();

		for (int i=0; i<reviewList.size(); i++){
			Review review = reviewList.get(i);
			String writerName = userRepository.findByUserId(review.getReviewWriter()).get().getUserName();
			String userName = review.getUser().getUserName();
			System.out.println("****"+writerName+" "+userName);
			ReviewRes reviewRes = new ReviewRes(review,writerName,userName);

			resList.add(reviewRes);
		}
		return resList;
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
		User user = userRepository.findByUserId(userId).orElse(null);
		return user;
	}

}
