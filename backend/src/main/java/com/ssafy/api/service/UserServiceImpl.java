package com.ssafy.api.service;

import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.db.entity.Auth;
import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import com.ssafy.db.repository.AuthRepository;
import io.swagger.annotations.ApiOperation;
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
	
//	@Autowired
////	UserRepositorySupport userRepositorySupport;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;

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
	public User updateUser(String userId, UserUpdatePatchReq updateInfo) {
		User user = getUserByUserId(userId);
		//찾았음
//		user.setDepartment(updateInfo.getDepartment());
//		user.setName(updateInfo.getName());
//		user.setPosition(updateInfo.getPosition());

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
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		//User user = userRepositorySupport.findUserByUserId(userId).get();
//		User user = userRepository.findByUserId(userId).get();
		User user = userRepository.findByUserId(userId).orElse(null);
		return user;
	}

}
