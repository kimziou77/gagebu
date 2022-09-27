package com.payhere.gagebu.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payhere.gagebu.common.annotation.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthApi {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck() {
        return "Healthy~";
    }

    @GetMapping("/auth-test")
    @ResponseStatus(HttpStatus.OK)
    public String authTest(@LoginUser Long userId) {
        return "[SecuredAPI] LoginID : " + userId;
    }
}
