package com.payhere.gagebu.domain.record.dto;

import com.payhere.gagebu.common.vo.Money;
import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordCreate;
import com.payhere.gagebu.domain.record.dto.RecordResponse.RecordInfo;
import com.payhere.gagebu.domain.record.model.Category;
import com.payhere.gagebu.domain.record.model.Record;
import com.payhere.gagebu.domain.user.model.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RecordConverter {

    public static Record createToRecord(RecordCreate dto, Long userId) {
        return Record.builder()
            .name(dto.name())
            .category(Category.valueOf(dto.category()))
            .money(new Money(dto.money()))
            .memo(dto.memo())
            .user(User.of(userId))
            .build();
    }

    public static RecordInfo recordToInfo(Record record) {
        return RecordInfo.builder()
            .name(record.getName())
            .category(String.valueOf(record.getCategory()))
            .money(record.getMoney().getValue())
            .memo(record.getMemo())
            .createdAt(record.getCreatedAt())
            .updatedAt(record.getUpdatedAt())
            .build();
    }
}
