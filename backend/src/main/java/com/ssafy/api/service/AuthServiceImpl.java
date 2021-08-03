package com.ssafy.api.service;

import com.ssafy.db.entity.Auth;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthRepository authRepository;

    @Override
    public Auth createUser(User user, String userEmail) {
        Auth auth = new Auth();
        auth.setUser(user);
        auth.setUserEmail(userEmail);
        return authRepository.save(auth);
    }

    @Override
    public String getUserIdByUserEmail(String userEmail) {
        // email로 auth table 조회
        Auth auth = authRepository.findByUserEmail(userEmail).orElse(null);
        if (auth == null) return null;	// null이면 null
        else return auth.getUser().getUserId();	// null이 아니면 userId 반환
    }
}
