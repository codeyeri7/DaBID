package com.ssafy.api.service;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("WishService")
public class WishServiceImpl implements WishService{

    @Autowired
    UserService userService;

    @Autowired
    LiveService liveService;

    @Override
    public List<WishList> getWishLives(String userId) {
        User user = userService.getUserByUserId(userId);
        return user.getWishList();
    }

    @Override
    public WishList putWishList(String userId, int prdId) {
        WishList wishList = new WishList();
        Live live = liveService.getLiveByPrdId(prdId);
        User user = userService.getUserByUserId(userId);
        wishList.setLive(live);
        wishList.setUser(user);
        return wishList;
    }
}
