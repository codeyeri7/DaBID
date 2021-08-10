package com.ssafy.api.service;

import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import java.util.List;

public interface WishService {

    List<WishList> getWishLives(String userId);
    void putWishLive(String userId, int prdId);
    void deleteWishLive(String userId, int prdId);

}
