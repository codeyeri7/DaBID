package com.ssafy.db.repository;

import com.ssafy.db.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Auth 관련 Spring Data JPA 인터페이스 정의.
 */
@Repository
//JpaRepository<>안에는 엔티티 클래스 이름과 ID 필드 타입이 지정된다.
public interface AuthRepository extends JpaRepository<Auth, String> {
    // UserEmail로 조회
    Optional<Auth> findByUserEmail(String userEmail);
}