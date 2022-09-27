package com.payhere.gagebu.domain.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payhere.gagebu.common.annotation.LoginUser;
import com.payhere.gagebu.domain.user.dto.UserResponse.UserInfo;
import com.payhere.gagebu.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfo getUserInfo(@PathVariable Long userId, @LoginUser Long loginId) {
        return userService.getUserInfo(userId, loginId);
    }
}
