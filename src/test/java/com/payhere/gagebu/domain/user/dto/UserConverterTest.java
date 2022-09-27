package com.payhere.gagebu.domain.user.dto;

import static com.payhere.gagebu.domain.user.dto.UserConverter.*;
import static com.payhere.gagebu.fixture.EntityGeneratorUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.domain.user.model.User;

class UserConverterTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("UserCreate -> User 변환 테스트")
    void createToUserTest() {
        UserCreate create = UserCreate.builder()
            .email("testEmail@test.com")
            .password("test-password")
            .build();

        User user = createToUser(passwordEncoder, create);

        user.validatePassword(passwordEncoder, create.password());
        assertThat(user.getEmail()).isEqualTo(create.email());
    }

    @Test
    @DisplayName("User -> UserInfo 변환 테스트")
    void userToInfoTest() {
        var password = "test-password";
        User user = makeUser(password);

        var info = userToInfo(user);
        assertAll(
            () -> assertThat(user.getId()).isEqualTo(info.userId()),
            () -> assertThat(user.getEmail()).isEqualTo(info.email())
        );
    }
}
