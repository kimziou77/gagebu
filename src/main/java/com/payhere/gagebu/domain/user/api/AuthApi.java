package com.payhere.gagebu.domain.user.api;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.domain.user.dto.UserRequest.UserLogin;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserLoginToken;
import com.payhere.gagebu.domain.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signUp(@Valid @RequestBody UserCreate userCreate) {
        var token = authService.signUp(userCreate);
        var url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        authService.sendAuthEmail(userCreate.email(), url, token);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public String verifyAuth(@PathParam(value = "token") String token, HttpServletResponse response) {
        var userId = authService.verifyUser(token);

        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userId)
            .toUri()
            .toString()
        );
        return "인증 완료되었습니다";
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginToken login(@Valid @RequestBody UserLogin user) {
        return authService.login(user);
    }

    @DeleteMapping("/auth/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout() {
        return "logout success";
    }

}
