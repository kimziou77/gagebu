package com.payhere.gagebu.security.jwt;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.payhere.gagebu.security.jwt.util.AuthorizationExtractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        var uri = request.getRequestURI();
        var httpMethod = request.getMethod();
        return uri.endsWith("login") || uri.endsWith("logout")
            || (uri.endsWith("users") && httpMethod.equalsIgnoreCase("POST"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        var accessToken = getAccessToken(req);
        if (accessToken != null) {
            var authentication = createAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(req, res);
    }

    private String getAccessToken(HttpServletRequest request) {
        var token = AuthorizationExtractor.extract(request);
        if (token != null) {
            jwtTokenGenerator.validateAccessToken(token);
        }
        return token;
    }

    private JwtAuthenticationToken createAuthentication(String accessToken) {
        var claims = jwtTokenGenerator.getClaims(accessToken);
        var userId = claims.get("userId", Long.class);
        var role = claims.get("role", String.class);

        return new JwtAuthenticationToken(
            toPrinciple(accessToken, userId), null, toAuthorities(role)
        );
    }

    private JwtAuthentication toPrinciple(String accessToken, Long userId) {
        return new JwtAuthentication(accessToken, userId);
    }

    private List<GrantedAuthority> toAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }

}
