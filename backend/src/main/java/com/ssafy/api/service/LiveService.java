package com.ssafy.api.service;

import com.ssafy.api.request.LiveRegisterPostReq;
import com.ssafy.api.request.LogPostReq;
import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *	라이브 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface LiveService {
    void createLive(User user, LiveRegisterPostReq liveRegisterPostReq);
    void updateLive(LiveRegisterPostReq liveRegisterPostReq, Live live);
    Live getLiveByPrdId(int prdId);
    void deleteLive(int prdId);
    List<Live> getRecentLives(int liveStatus);
    Page<Live> getAllLives(int page);
    List<Live> searchLives(List<String> category, List<String> liveStatuses, String keyword);
    void createLog(LogPostReq logInfo);
}
