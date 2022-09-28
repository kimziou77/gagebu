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
import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordCreate;
import com.payhere.gagebu.domain.record.service.RecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecordApi {

    private final RecordService recordService;

    @PostMapping("/records")
    @ResponseStatus(HttpStatus.CREATED)
    public RecordCreated createRecord(@Valid @RequestBody RecordCreate req, @LoginUser Long userId, HttpServletResponse response) {
        var created = recordService.createRecord(req, userId);
        response.setHeader("Location", generatedUri(created.id()).toString());
        return created;
    }

    @DeleteMapping("/records/{recordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable Long recordId, @LoginUser Long userId) {
        recordService.deleteRecord(recordId, userId);
    }

    @PatchMapping("/records/{recordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editRecord(@PathVariable Long recordId, @LoginUser Long userId, @Valid @RequestBody RecordRequest.RecordEdit req) {
        recordService.editRecord(recordId, userId, req);
    }

    @GetMapping("/records/{recordId}")
    @ResponseStatus(HttpStatus.OK)
    public RecordInfo getRecord(@PathVariable Long recordId, @LoginUser Long userId) {
        return recordService.findDetailRecord(recordId, userId);
    }

    @GetMapping("/records")
    @ResponseStatus(HttpStatus.OK)
    public RecordInfoList getAllRecords(@LoginUser Long userId) {
        return recordService.findAllRecords(userId);
    }

}
