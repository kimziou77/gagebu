package com.payhere.gagebu.domain.record.dto;

import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordCreate;
import com.payhere.gagebu.domain.record.dto.RecordResponse.RecordInfo;
import com.payhere.gagebu.domain.record.model.Record;
import com.payhere.gagebu.domain.user.model.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RecordConverter {

    public static Record createToRecord(RecordCreate dto, Long userId) {
        return Record.builder()
            .name(dto.name())
            .money(dto.money())
            .memo(dto.memo())
            .user(User.of(userId))
            .build();
    }

    public static RecordInfo recordToInfo(Record record) {
        return RecordInfo.builder()
            .name(record.getName())
            .money(record.getMoney())
            .memo(record.getMemo())
            .createdAt(record.getCreatedAt())
            .updatedAt(record.getUpdatedAt())
            .build();
    }
}
