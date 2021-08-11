package com.ssafy.api.service;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import java.util.List;

public interface WishService {

    List<WishList> getWishLives(User user);
    void putWishLive(User user, int prdId);
    void deleteWishLive(User user, int prdId);
    boolean checkWishLive(User user, int prdId);
    List<WishList> getBestLives();

}
