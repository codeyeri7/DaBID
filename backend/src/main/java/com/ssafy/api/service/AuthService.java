package com.ssafy.api.service;


import com.ssafy.db.entity.Auth;
import com.ssafy.db.entity.User;

public interface AuthService {
    Auth createUser(User user, String userEmail);
    String getUserIdByUserEmail(String userEmail);

}
