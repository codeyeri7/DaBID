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
    public List<WishList> getWishLives(User user) {
        List<WishList> list =wishListRepository.findByUser(user).orElseGet(null);
        return list;
    }

    @Override
    public boolean checkWishLive(User user, int prdId) {
            Live live = liveService.getLiveByPrdId(prdId);
            int check = wishListRepository.countByUserAndLive(user,live);

            //찜했으면 true, 찜한 거 아니면 false
             if(wishListRepository.countByUserAndLive(user,live)==0) return false;
             else return true;
    }

//    @Override
//    public List<Live> getBestLives() {
//       List<WishList> list = wishListRepository.findTop2ByLiveOrderByLive().orElseGet(null);
//        System.out.println(list.get(0).getWishId());
//        return null;
//    }

    @Override
    public void putWishLive(User user, int prdId) {
        System.out.println("여기들어와2");
        WishList wishList = new WishList();
        Live live = liveService.getLiveByPrdId(prdId);
        wishList.setLive(live);
        wishList.setUser(user);

        //중복되면 안되니까 이미 찜한 목록은 찜할 수 없도록
        if(wishListRepository.countByUserAndLive(user,live)==0) wishListRepository.save(wishList);
        else return;
    }

    @Override
    public void deleteWishLive(User user, int prdId) {
        Live live = liveService.getLiveByPrdId(prdId);
        WishList wishList = wishListRepository.findByUserAndLive(user,live).orElse(null);
        System.out.println("삭제할wish"+wishList.getWishId());
        wishListRepository.delete(wishList);
    }
}
