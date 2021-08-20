package com.ssafy.api.service;

import com.ssafy.api.request.LiveRegisterPostReq;
import com.ssafy.api.request.ResultPostReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.LiveRepository;
import com.ssafy.db.repository.LiveStatusRepository;
import com.ssafy.db.repository.ResultRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ResultService")
public class ResultService {

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    LiveStatusRepository liveStatusRepository;

    public Result createResult(int prdId, ResultPostReq resultInfo) {
        Result result = new Result();
        Live live = liveRepository.findByPrdId(prdId).get();
        result.setPrdId(prdId);
        result.setLive(live);
        result.setBuyer(userRepository.findByUserId(resultInfo.getBuyerId()).get());
        result.setSeller(userRepository.findByUserId(resultInfo.getSellerId()).get());
        result.setResPriceEnd(resultInfo.getResPriceEnd());
        resultRepository.save(result);

        live.setLiveStatus(liveStatusRepository.getOne(2));
        live.setResult(result);
        liveRepository.save(live);
        return result;
    }

    public List<Result> findAllResult(String userId) {
        User user = userRepository.findByUserId(userId).get();
        List<Result> resultList = resultRepository.findBySellerOrBuyer(user, user);
        return resultList;
    }

    public Result findResultById(int prdId) {
        Result result = resultRepository.findByPrdId(prdId).orElseGet(null);
        return result;
    }

}
