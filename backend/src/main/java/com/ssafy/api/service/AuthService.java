package com.ssafy.api.service;


import com.ssafy.db.entity.Auth;

public interface AuthService {
    Auth createUser(String userId, String userEmail);
    String getUserIdByUserEmail(String userEmail);

}
