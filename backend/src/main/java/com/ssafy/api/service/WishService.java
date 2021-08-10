package com.ssafy.api.service;

import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import java.util.List;

public interface WishService {

    List<WishList> getWishLives(String userId);
    WishList putWishList(String userId, int prdId);

}
