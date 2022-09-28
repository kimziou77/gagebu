package com.payhere.gagebu.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;

public interface UserRequest {

    record UserCreate(
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "비밀번호는 비어있을 수 없습니다")
        @Size(max = 20, message = "비밀번호는 20자 이내로 입력해주세요")
        String password
    ) {

        @Builder
        public UserCreate {
        }
    }

    record UserLogin(
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        
        @NotBlank(message = "비밀번호는 비어있을 수 없습니다")
        @Size(max = 20, message = "비밀번호는 20자 이내로 입력해주세요")
        String password
    ) {

        @Builder
        public UserLogin {
        }
    }
}
