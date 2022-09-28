package com.payhere.gagebu.domain.record.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

public interface RecordResponse {

    record RecordInfo(
        String name,
        Integer money,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

        @Builder
        public RecordInfo {
        }
    }

    record RecordInfoList(
        List<RecordInfo> records
    ) {

        @Builder
        public RecordInfoList {
        }
    }

    record RecordCreated(
        Long id
    ) {

        @Builder
        public RecordCreated {
        }
    }
}
