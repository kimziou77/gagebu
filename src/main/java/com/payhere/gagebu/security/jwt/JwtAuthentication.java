package com.payhere.gagebu.security.jwt;

import static com.google.common.base.Preconditions.*;

import java.util.Objects;

import com.payhere.gagebu.security.jwt.exception.InvalidTokenException;

public record JwtAuthentication(String token, Long id) {

    public JwtAuthentication {
        checkArgument(id != null && id > 0L, "잘못된 유저 인증 객체 입니다");
        validateToken(token);
    }

    private void validateToken(String token) {
        if (Objects.isNull(token) || token.isBlank()) {
            throw new InvalidTokenException();
        }
    }

}
