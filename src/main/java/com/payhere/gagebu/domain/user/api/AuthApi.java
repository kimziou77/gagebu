package com.payhere.gagebu.domain.user.api;

import static com.payhere.gagebu.common.util.ApiUtil.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.domain.user.dto.UserRequest.UserLogin;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserCreated;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserLoginToken;
import com.payhere.gagebu.domain.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    // 회원가입 -> 유저 생성
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreated signUp(@Valid @RequestBody UserCreate userCreate, HttpServletResponse response) {
        var created = authService.signUp(userCreate);
        response.setHeader("Location", generatedUri(created.id()).toString());
        return created;
    }

    // 로그인 -> 토큰 발급
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginToken login(@Valid @RequestBody UserLogin user) {
        return authService.login(user);
    }

    // 로그아웃 -> ?
    @DeleteMapping("/auth/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout() {
        return "logout success";
    }

}
