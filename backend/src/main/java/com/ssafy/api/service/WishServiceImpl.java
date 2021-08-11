package com.ssafy.api.service;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishList;
import com.ssafy.db.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("WishService")
public class WishServiceImpl implements WishService{

    @Autowired
    UserService userService;
    @Autowired
    LiveService liveService;

    @Autowired
    WishListRepository wishListRepository;


    @Override
    public List<WishList> getWishLives(String userId) {
        User user = userService.getUserByUserId(userId);
        List<WishList> list =wishListRepository.findByUser(user).orElseGet(null);
        return list;
    }

    @Override
    public void putWishLive(String userId, int prdId) {
        WishList wishList = new WishList();
        Live live = liveService.getLiveByPrdId(prdId);
        User user = userService.getUserByUserId(userId);
        wishList.setLive(live);
        wishList.setUser(user);

        wishListRepository.save(wishList);
    }

    @Override
    public void deleteWishLive(String userId, int prdId) {
        Live live = liveService.getLiveByPrdId(prdId);
        User user = userService.getUserByUserId(userId);
        WishList wishList = wishListRepository.findByUserAndLive(user,live);
        wishListRepository.delete(wishList);
    }
}
