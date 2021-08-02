package com.ssafy.db.repository;

import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User 관련 DB 쿼리 메소드 이름을 자동 분석해서 JPQL을 생성 및 실행을 위한 Spring Data JPA 인터페이스 정의.
 */
@Repository
//JpaRepository<>안에는 엔티티 클래스 이름과 ID 필드 타입이 지정된다. 기본형의 경우 wrapper class
public interface UserRepository extends JpaRepository<User, String> {
    // 아래와 같이, Query Method 인터페이스(반환값, 메소드명, 인자) 정의를 하면 자동으로 Query Method 구현됨.
    Optional<User> findByUserId(String userId);
}