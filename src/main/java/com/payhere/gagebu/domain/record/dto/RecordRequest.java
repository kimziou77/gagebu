package com.payhere.gagebu.domain.record.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Builder;

public interface RecordRequest {

    record RecordCreate(
        @Size(max = 20, message = "거래내역 이름은 최대 20자 입니다.")
        String name,

        @PositiveOrZero(message = "거래금액은 0원 이상이어야 합니다.")
        @Max(value = 100_000_000, message = "최대 거래금액은 1억원 입니다")
        Integer money,

        @Size(max = 60, message = "거래내역 메모는 최대 60자 입니다.")
        String memo
    ) {

        @Builder
        public RecordCreate {
        }
    }

    record RecordEdit(
        @PositiveOrZero(message = "거래금액은 0원 이상이어야 합니다.")
        @Max(value = 100_000_000, message = "최대 거래금액은 1억원 입니다")
        Integer money,

        @Size(max = 60, message = "거래내역 메모는 최대 60자 입니다.")
        String memo
    ) {

        @Builder
        public RecordEdit {
        }
    }
}
