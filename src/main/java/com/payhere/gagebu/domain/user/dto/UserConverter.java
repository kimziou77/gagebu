package com.payhere.gagebu.domain.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserInfo;
import com.payhere.gagebu.domain.user.model.User;
import com.payhere.gagebu.domain.user.model.UserRole;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {

    public static User createToUser(PasswordEncoder passwordEncoder, UserCreate dto) {
        return User.builder()
            .email(dto.email())
            .password(passwordEncoder.encode(dto.password()))
            .userRole(UserRole.LOGIN)
            .build();
    }

    public static UserInfo userToInfo(User user) {
        return UserInfo.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .build();
    }
}
