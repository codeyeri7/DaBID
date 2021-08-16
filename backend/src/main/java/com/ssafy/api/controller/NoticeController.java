package com.ssafy.api.controller;

import com.ssafy.api.request.NoticePostReq;
import com.ssafy.api.service.NoticeService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Notice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 공지 관련 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value="Notice API", tags={"Notice"})
@RestController
@RequestMapping("/dabid/notice")
public class NoticeController {
    /**
     * 공지 Read 관련 Controller
     */
    @Autowired
    NoticeService noticeService;

    @GetMapping("")
    @ApiOperation(value = "공지 조회", notes = "공지사항 조회")
    public ResponseEntity<?> selectNotice(@RequestParam(name="page") int page){

        return ResponseEntity.status(200).body(noticeService.findAll(page));

    }

    @PostMapping("")
    @ApiOperation(value ="공지 등록", notes = "공지 등록" )
    public ResponseEntity<?> createNotice(
            @RequestBody @ApiParam(value="공지 생성을 위한 정보", required = true) NoticePostReq noticePostReq){
        Notice notice = new Notice();
        noticeService.createNotice(noticePostReq);
        return ResponseEntity.status(200).body(notice);
    }

    @PatchMapping("/{noticeId}")
    @ApiOperation(value ="공지 수정", notes = "공지 수정" )
    public ResponseEntity<?> createNotice(@RequestParam @ApiParam(name="noticeId") int noticeId,
                                          @RequestBody @ApiParam(value="라이브 생성을 위한 정보", required = true) NoticePostReq noticeInfo){

        Notice notice = noticeService.getNotice(noticeId);
        noticeService.updateNotice(noticeInfo,notice);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/{noticeId}")
    @ApiOperation(value ="공지 삭제", notes = "공지 삭제" )
    public ResponseEntity<?> deleteNotice(
            @RequestParam("noticeId") @ApiParam(value = "삭제를 위한 공지 고유 아이디", readOnly = true) int noticeId){

        noticeService.deleteNotice(noticeId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}
