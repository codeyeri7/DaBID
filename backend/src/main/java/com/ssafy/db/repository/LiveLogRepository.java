package com.ssafy.db.repository;

import com.ssafy.db.entity.LiveLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 라이브 로그 관련 Spring Data JPA 인터페이스 정의.
 */
@Repository
public interface LiveLogRepository extends JpaRepository<LiveLog, Integer> {

}