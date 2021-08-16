package com.ssafy.api.service;

import com.ssafy.api.request.NoticePostReq;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의..
 */

@Service("noticeService")

public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeRepository noticeRepository;

	@Override
	public Page<Notice> findAll(int page) {
		return noticeRepository.findAll(PageRequest.of(page, 10));
	}

	@Override
	public void createNotice(NoticePostReq noticeInfo) {
		Notice notice = new Notice();

		notice.setNoticeTitle(noticeInfo.getNoticeTitle());
		notice.setNoticeDate(noticeInfo.getNoticeDate());
		notice.setNoticeType(noticeInfo.getNoticeType());
		notice.setNoticeContent(noticeInfo.getNoticeContent());

		noticeRepository.save(notice);
	}

	@Override
	public Notice getNotice(int noticeId) {
		return noticeRepository.findById(noticeId).get();
	}

	@Override
	public void updateNotice(NoticePostReq noticePostReq, Notice notice) {
		notice.setNoticeTitle(noticePostReq.getNoticeTitle());
		notice.setNoticeDate(noticePostReq.getNoticeDate());
		notice.setNoticeType(noticePostReq.getNoticeType());
		notice.setNoticeContent(noticePostReq.getNoticeContent());

		noticeRepository.save(notice);
	}

	@Override
	public void deleteNotice(int noticeId) {
		noticeRepository.delete(noticeRepository.getOne(noticeId));
	}
}
