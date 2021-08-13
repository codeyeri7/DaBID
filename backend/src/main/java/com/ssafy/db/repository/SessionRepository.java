package com.ssafy.db.repository;

import com.ssafy.db.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository  extends JpaRepository<Session, String> {

}
