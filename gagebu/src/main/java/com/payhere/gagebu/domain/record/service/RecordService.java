package com.payhere.gagebu.domain.record.service;

import static com.payhere.gagebu.domain.record.dto.RecordConverter.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payhere.gagebu.domain.record.dto.RecordConverter;
import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordCreate;
import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordEdit;
import com.payhere.gagebu.domain.record.dto.RecordResponse;
import com.payhere.gagebu.domain.record.dto.RecordResponse.RecordInfo;
import com.payhere.gagebu.domain.record.dto.RecordResponse.RecordInfoList;
import com.payhere.gagebu.domain.record.exception.RecordNotFoundException;
import com.payhere.gagebu.domain.record.repository.RecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public RecordResponse.RecordCreate createRecord(RecordCreate dto, Long userId) {
        var savedRecord = recordRepository.save(createToRecord(dto, userId));
        return RecordResponse.RecordCreate.builder().recordId(savedRecord.getId()).build();
    }

    @Transactional
    public void editRecord(Long recordId, Long userId, RecordEdit req) {
        var record = recordRepository.findById(recordId)
            .orElseThrow(RecordNotFoundException::new);
        record.validateUserPermission(userId);
        record.editMoney(req.money());
        record.editMemo(req.memo());
    }

    @Transactional
    public void deleteRecord(Long recordId, Long userId) {
        var record = recordRepository.findById(recordId)
            .orElseThrow(RecordNotFoundException::new);
        record.validateUserPermission(userId);
        recordRepository.deleteById(recordId);
    }

    public RecordInfo findDetailRecord(Long recordId, Long userId) {
        var record = recordRepository.findById(recordId)
            .orElseThrow(RecordNotFoundException::new);
        record.validateUserPermission(userId);
        return recordToInfo(record);
    }

    public RecordInfoList findAllRecords(Long userId) {
        var records = recordRepository.findByUserId(userId)
            .stream().map(RecordConverter::recordToInfo)
            .toList();

        return RecordInfoList.builder().records(records).build();
    }
}
