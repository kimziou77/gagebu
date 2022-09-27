package com.payhere.gagebu.domain.record.dto;

import lombok.Builder;

public interface RecordRequest {

    record RecordCreate(
        String name,
        String category,
        Integer money,
        String memo
    ) {

        @Builder
        public RecordCreate {
        }
    }

    record RecordEdit(
        Integer money,
        String memo
    ) {

        @Builder
        public RecordEdit {
        }
    }
}
