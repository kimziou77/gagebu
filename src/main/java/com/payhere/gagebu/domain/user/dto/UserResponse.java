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

    record UserCreated(
        Long id
    ) {

        @Builder
        public UserCreated {
        }
    }

    record UserLoginToken(
        String accessToken
    ) {

        @Builder
        public UserLoginToken {
        }
    }
}
