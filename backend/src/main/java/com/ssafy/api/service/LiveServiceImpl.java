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
	public void createLive(String userId, LiveRegisterPostReq liveInfo) {
		Live live = new Live();
		live.setPrdSellerId(userId);					// 판매자 고유 아이디
		live.setLiveTitle(liveInfo.getLiveTitle());		// 라이브 제목
		live.setLiveDesc(liveInfo.getLiveDesc());		// 라이브 상세 정보
		live.setLiveDate(liveInfo.getLiveDate());		// 라이브 시작 날짜
		live.setPrdName(liveInfo.getPrdName());			// 상품명
		live.setPrdCategory(liveInfo.getPrdCategory()); // 카테고리 번호
		live.setPrdNo(liveInfo.getPrdNo());				// 상품 일련 번호

		live.setPrdPriceStart(live.getPrdPriceStart());	// 경매 시작 가격
	}

	@Override
	public void updateLive(LiveRegisterPostReq liveInfo, Live live) {
		live.setLiveTitle(liveInfo.getLiveTitle());
		live.setLiveDesc(liveInfo.getLiveDesc());
		live.setLiveDate(liveInfo.getLiveDate());
		live.setPrdName(liveInfo.getPrdNo());
		live.setPrdCategory(liveInfo.getPrdCategory());
		live.setPrdNo(liveInfo.getPrdNo());
		live.setPrdPhoto(liveInfo.getPrdPhoto());
		live.setPrdPriceStart(liveInfo.getPrdPriceStart());
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
