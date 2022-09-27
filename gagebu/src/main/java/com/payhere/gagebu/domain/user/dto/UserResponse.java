package com.payhere.gagebu.domain.user.dto;

import lombok.Builder;

public interface UserResponse {

    record UserInfo(
        Long userId,
        String email
    ) {

        @Builder
        public UserInfo {
        }
    }

}
