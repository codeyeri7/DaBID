package com.ssafy.db.repository;

import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * WishList 관련 DB 쿼리 메소드 이름을 자동 분석해서 JPQL을 생성 및 실행을 위한 Spring Data JPA 인터페이스 정의.
 */

@Repository
public interface WishListRepository extends JpaRepository<WishList,Integer> {

}
