package com.ssafy.api.service;

import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.repository.UserRepository;

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
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
//		user.setDepartment(userRegisterInfo.getDepartment());
//		user.setName(userRegisterInfo.getName());
//		user.setPosition(userRegisterInfo.getPosition());
//		user.setUserId(userRegisterInfo.getUserId());
//
//		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));

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
