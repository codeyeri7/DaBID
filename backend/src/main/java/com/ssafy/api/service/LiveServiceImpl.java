package com.ssafy.api.service;

import com.ssafy.api.request.LiveRegisterPostReq;
import com.ssafy.db.entity.Live;
import com.ssafy.db.entity.LiveStatus;
import com.ssafy.db.entity.ProductCategory;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.LiveRepository;
import com.ssafy.db.repository.LiveStatusRepository;
import com.ssafy.db.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public void createLive(User user, LiveRegisterPostReq liveInfo) {
		Live live = new Live();
//		live.setPrdSellerId(user.getUserId());			// 판매자 고유 아이디
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

//		live.setPrdCategory(liveInfo.getPrdCategory()); // 카테고리 번호

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
		System.out.println("");
		Timestamp timestamp = Timestamp.valueOf(liveDate);
		live.setLiveDate(timestamp);

		Optional<ProductCategory> productcategory = productCategoryRepository.findByPrdCategory(liveInfo.getPrdCategory());
		live.setProductCategory(productcategory.orElseGet(null));

		live.setPrdName(liveInfo.getPrdName());

		Optional<LiveStatus> livestatus = liveStatusRepository.findByLiveStatus(0);
		live.setLiveStatus(livestatus.orElseGet(null));

		//live.setPrdCategory(liveInfo.getPrdCategory());
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
	public List<Live> getAllLives() {
		List<Live> list = liveRepository.findAll();
		return list;
	}

	@Override
	public List<Live> searchLives(List<String> categories, String keyword) {
		List<Live> liveList = new ArrayList<>();

		if (categories != null) {
			// 카테고리 필터링이 있다면
			List<ProductCategory> prdCategories = new ArrayList<>();
			for (String category : categories){
				prdCategories.add(productCategoryRepository.findByPrdCategoryName(category));
			}
			liveList.addAll(liveRepository.findByProductCategoryIn(prdCategories).orElseGet(null));

			// 검색 키워드도 있다면
			if (keyword != null) {
				liveList.retainAll(liveRepository.findByLiveTitleContainingOrPrdNameContaining(keyword, keyword).orElseGet(null));
			}

		} else if (keyword != null) {
			// 카테고리 X
			// 검색 키워드만 있다면
			liveList.addAll(liveRepository.findByLiveTitleContainingOrPrdNameContaining(keyword, keyword).orElseGet(null));

		} else {
			return liveRepository.findAll();
		}

		return liveList;

//		// 검색어가 없었다면
//		if (categories == null && keyword == null) {
//			// 전체 조회
//			return liveRepository.findAll();
//		}
//
//		// 카테고리 검색어만 있었다면
//		if (categories != null && keyword == null) {
//			return liveRepository.findByProductCategoryIn(prdCategories).orElseGet(null);
//		}
//
//		// 키워드 검색어만 있었다면
//		if (keyword != null && categories == null) {
//			// 라이브 제목이나 상품 이름이 포함하고 있는지 검색
//			return liveRepository.findByLiveTitleContainingOrPrdNameContaining(keyword, keyword).orElseGet(null);
//		}
//
//		// 카테고리 & 키워드
//		if (categories != null && keyword != null) {
//			// 카테고리 검색
//			liveList.addAll(liveRepository.findByProductCategoryIn(prdCategories).orElseGet(null));
//
//			// 키워드 검색과 교집합
//			liveList.retainAll(liveRepository.findByLiveTitleContainingOrPrdNameContaining(keyword, keyword).orElseGet(null));
//			return liveList;
//		}
	}
}
