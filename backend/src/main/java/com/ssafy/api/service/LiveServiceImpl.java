package com.ssafy.api.service;

import com.ssafy.api.request.LiveRegisterPostReq;
import com.ssafy.db.entity.Live;
import com.ssafy.db.repository.LiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의..
 */
@Service("liveService")
public class LiveServiceImpl implements LiveService {
	@Autowired
	LiveRepository liveRepository;

	@Override
	public void createLive(LiveRegisterPostReq liveInfo) {

	}

	@Override
	public void updateLive(LiveRegisterPostReq liveInfo, Live live) {
//
	}

	@Override
	public void deleteLive(int prdId) {
		liveRepository.delete(getLiveByPrdId(prdId));
	}

	@Override
	public Live getLiveByPrdId(int prdId) {
		Live live = liveRepository.findByPrdId(prdId).orElse(null);
		return live;
	}

	@Override
	public List<Live> getAllLives() {
		return liveRepository.findAll();
	}
}
