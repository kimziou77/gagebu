package com.payhere.gagebu.domain.user.dto;

import lombok.Builder;

public interface UserRequest {

    record UserCreate(
        String email,
        String password
    ) {

        @Builder
        public UserCreate {
        }
    }

    record UserLogin(
        String email,
        String password
    ) {

        @Builder
        public UserLogin {
        }
    }
}
