package com.ssafy.api.service;

import com.ssafy.api.request.LiveRegisterPostReq;
import com.ssafy.api.request.LogPostReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.LiveLogRepository;
import com.ssafy.db.repository.LiveRepository;
import com.ssafy.db.repository.LiveStatusRepository;
import com.ssafy.db.repository.ProductCategoryRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의..
 */
@Service("liveService")
public class LiveServiceImpl implements LiveService {
	@Autowired
	LiveRepository liveRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	@Autowired
	LiveStatusRepository liveStatusRepository;
	@Autowired
	LiveLogRepository liveLogRepository;

	@Override
	public void createLive(User user, LiveRegisterPostReq liveInfo) {
		Live live = new Live();
		live.setUser(user);
		live.setLiveTitle(liveInfo.getLiveTitle());        // 라이브 제목
		live.setLiveDesc(liveInfo.getLiveDesc());        // 라이브 상세 정보

		// 프론트에서 날짜, 시간 따로 String으로 받은 후 합쳐서 저장
		// input 날짜: 2021-08-08
		// input 시간: 12:00
		// 2021-08-08 12:00:00
		String liveDate = liveInfo.getLiveDate() + " " + liveInfo.getLiveTime() + ":00";
		Timestamp timestamp = Timestamp.valueOf(liveDate);
		live.setLiveDate(timestamp);                    // 라이브 시작 날짜
		live.setPrdName(liveInfo.getPrdName());            // 상품명

		Optional<ProductCategory> productcategory = productCategoryRepository.findByPrdCategory(liveInfo.getPrdCategory());
		live.setProductCategory(productcategory.orElseGet(null));

		Optional<LiveStatus> livestatus = liveStatusRepository.findByLiveStatus(0);
		live.setLiveStatus(livestatus.orElseGet(null));

		live.setPrdNo(liveInfo.getPrdNo());                // 상품 일련 번호
		live.setPrdPriceStart(liveInfo.getPrdPriceStart());    // 경매 시작 가격
		live.setPrdPhoto(liveInfo.getPrdPhoto()); //상품사진

		List<Live> liveList = user.getLiveList();
		liveList.add(live);
		user.setLiveList(liveList);
		liveRepository.save(live);
	}

	@Override
	public void updateLive(LiveRegisterPostReq liveInfo, Live live) {
		live.setLiveTitle(liveInfo.getLiveTitle());
		live.setLiveDesc(liveInfo.getLiveDesc());

		String liveDate = liveInfo.getLiveDate() + " " + liveInfo.getLiveTime() + ":00";
		System.out.println(liveInfo.getPrdPhoto());
		System.out.println(liveInfo.getPrdCategory());
		Timestamp timestamp = Timestamp.valueOf(liveDate);
		live.setLiveDate(timestamp);

		Optional<ProductCategory> productcategory = productCategoryRepository.findByPrdCategory(liveInfo.getPrdCategory());
		live.setProductCategory(productcategory.orElseGet(null));

		live.setPrdName(liveInfo.getPrdName());

		Optional<LiveStatus> livestatus = liveStatusRepository.findByLiveStatus(0);
		live.setLiveStatus(livestatus.orElseGet(null));

		live.setPrdNo(liveInfo.getPrdNo());
		live.setPrdPriceStart(liveInfo.getPrdPriceStart());
		live.setPrdPhoto(liveInfo.getPrdPhoto());

		liveRepository.save(live);
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
	public List<Live> getRecentLives(int liveStatus) {
		LiveStatus ls = liveStatusRepository.findByLiveStatus(liveStatus).orElseGet(null);
		List<Live> list = liveRepository.findTop2ByLiveStatusOrderByLiveDateAsc(ls).orElseGet(null);
		return list;
	}

	@Override
	public Page<Live> getAllLives(int page) {
		return liveRepository.findAll(PageRequest.of(page, 6));
	}

	@Override
	public List<Live> searchLives(List<String> categories, List<String> statuses, String keyword) {
		// 검색 기준 모두 3개
		// 카테고리 / 라이브 상태 / 키워드

		// 카테고리 / 라이브 상태 / 키워드 모두 없을 때
		List<Live> liveList = liveRepository.findAll();

		// 카테고리로 라이브 검색
		if (categories != null && !categories.isEmpty()) {
			liveList.retainAll(this.searchLiveByCategory(categories));
		}

		// 라이브 상태로 라이브 검색
		if (statuses != null && !statuses.isEmpty()) {
			liveList.retainAll(this.searchLiveByLiveStatus(statuses));
		}

		// 검색 키워드로 라이브 검색 (라이브 제목, 상품명)
		if (keyword != null && !keyword.equals("")) {
			liveList.retainAll(this.searchLiveByKeyword(keyword));
		}

		// 필터링되고 남은 live 반환
		return liveList;
	}

	public List<Live> searchLiveByCategory(List<String> categories) {
		// Front에서 String으로 받아온 카테고리로 ProductCategory 조회
		List<ProductCategory> prdCategories = new ArrayList<>();
		for (String category : categories){
			prdCategories.add(productCategoryRepository.findByPrdCategoryName(category));
		}
		// 조회한 ProductCategory로 live 조회
		return liveRepository.findByProductCategoryIn(prdCategories).orElseGet(null);
	}

	public List<Live> searchLiveByLiveStatus(List<String> statuses) {
		// Front에서 String으로 받아온 라이브 상태로 LiveStatus 조회
		List<LiveStatus> liveStatus = new ArrayList<>();
		for (String status : statuses){
			liveStatus.add(liveStatusRepository.findByLiveStatusName(status).orElseGet(null));
		}
		// 조회한 LiveStatus로 live 조회
		return liveRepository.findByLiveStatusIn(liveStatus).orElseGet(null);
	}

	public List<Live> searchLiveByKeyword(String keyword) {
		// Front에서 받아온 keyword로
		// 라이브 제목, 상품명 like %keyword% 검색
		return liveRepository.findByLiveTitleContainingOrPrdNameContaining(keyword, keyword).orElseGet(null);
	}

	public void createLog(LogPostReq logInfo) {
		LiveLog log = new LiveLog();
		log.setBidder(logInfo.getBidder());
		log.setBidPrice(logInfo.getBidPrice());
		log.setLive(liveRepository.findByPrdId(logInfo.getPrdId()).get());

		liveLogRepository.save(log);
	}

	public void updateStatus(int prdId){
		Live live = liveRepository.findByPrdId(prdId).get();
		live.setLiveStatus(liveStatusRepository.getOne(1));
		liveRepository.save(live);
	}

	public void endLive(int prdId){
		Live live = liveRepository.findByPrdId(prdId).get();
		live.setLiveStatus(liveStatusRepository.getOne(2));
		liveRepository.save(live);
	}
}
