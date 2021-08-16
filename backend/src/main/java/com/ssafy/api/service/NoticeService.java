package com.ssafy.api.service;


import com.ssafy.api.request.NoticePostReq;
import com.ssafy.db.entity.Notice;
import org.springframework.data.domain.Page;

public interface NoticeService {
    Page<Notice> findAll(int page);
    void createNotice(NoticePostReq noticePostReq);
    Notice getNotice(int noticeId);
    void updateNotice(NoticePostReq noticePostReq, Notice notice);
    void deleteNotice(int noticeId);
}
