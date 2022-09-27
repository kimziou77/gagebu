package com.payhere.gagebu.domain.record.api;

import static com.payhere.gagebu.common.util.ApiUtil.*;
import static com.payhere.gagebu.domain.record.dto.RecordResponse.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payhere.gagebu.common.annotation.LoginUser;
import com.payhere.gagebu.domain.record.dto.RecordRequest;
import com.payhere.gagebu.domain.record.dto.RecordResponse;
import com.payhere.gagebu.domain.record.service.RecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecordApi {

    private final RecordService recordService;

    // 다중 조회
    @GetMapping("/records")
    @ResponseStatus(HttpStatus.OK)
    public RecordInfoList getAllRecords(@LoginUser Long userId) {
        return recordService.findAllRecords(userId);
    }

    // 단건 조회
    @GetMapping("/records/{recordId}")
    @ResponseStatus(HttpStatus.OK)
    public RecordInfo getRecord(@PathVariable Long recordId, @LoginUser Long userId) {
        return recordService.findDetailRecord(recordId, userId);
    }

    // 생성
    @PostMapping("/records")
    @ResponseStatus(HttpStatus.CREATED)
    public RecordResponse.RecordCreate createRecord(HttpServletResponse response, @Valid @RequestBody RecordRequest.RecordCreate req, @LoginUser Long userId) {
        var createdId = recordService.createRecord(req, userId);
        response.setHeader("location", generatedUri(createdId.recordId()).toString());
        return createdId;
    }

    // 삭제
    @DeleteMapping("/records/{recordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCheck(@PathVariable Long recordId, @LoginUser Long userId) {
        recordService.deleteRecord(recordId, userId);
    }
    
    // 부분 수정
    @PatchMapping("/records/{recordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putCheck(@PathVariable Long recordId, @LoginUser Long userId, @Valid @RequestBody RecordRequest.RecordEdit req) {
        recordService.editRecord(recordId, userId, req);
    }
}
