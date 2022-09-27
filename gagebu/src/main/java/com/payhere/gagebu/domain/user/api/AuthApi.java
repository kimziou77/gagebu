package com.payhere.gagebu.domain.user.api;

import static com.payhere.gagebu.common.util.ApiUtil.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payhere.gagebu.domain.user.dto.UserRequest;
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
    public Long signUp(HttpServletResponse response, @Valid @RequestBody UserRequest.UserCreate userCreate) {
        var createdId = authService.signUp(userCreate);
        response.setHeader("location", generatedUri(createdId).toString());
        return createdId;
    }

    // 로그인 -> 토큰 발급
    @GetMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@Valid @RequestBody UserRequest.UserLogin user) {
        return authService.login(user);
    }

    // 로그아웃 -> ?
    @DeleteMapping("/auth/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout() {
        return "logout success";
    }

}
