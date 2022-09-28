package com.payhere.gagebu.common.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payhere.gagebu.common.annotation.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthApi {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    public void redirectToSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/docs/index.html")
            .build()
            .toUri()
            .toString()
        );
    }

    @GetMapping("/auth-test")
    @ResponseStatus(HttpStatus.OK)
    public String authTest(@LoginUser Long userId) {
        return "[SecuredAPI] LoginID : " + userId;
    }
}
