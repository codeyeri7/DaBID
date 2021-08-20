package com.ssafy.db.repository;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.Result;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    Optional<Result> findByPrdId(int prdId);
    Result findByLive(Live live);
    List<Result> findBySellerOrBuyer(User seller, User buyer);
}
