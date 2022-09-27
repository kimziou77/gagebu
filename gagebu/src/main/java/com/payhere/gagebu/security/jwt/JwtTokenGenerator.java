package com.payhere.gagebu.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.payhere.gagebu.security.jwt.exception.ExpiredAccessTokenException;
import com.payhere.gagebu.security.jwt.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenGenerator {

    private final String issuer;

    private final String secretKey;

    private final long accessTokenValidTime;

    public JwtTokenGenerator(
        @Value("${jwt.issuer}") String issuer,
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.access-token.expire-length}") long accessTokenValidTime
    ) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.accessTokenValidTime = accessTokenValidTime;
    }

    private String createToken(Map<String, Object> claims, long expireTime) {
        var now = new Date();
        return Jwts.builder()
            .setIssuer(issuer)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expireTime))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    public String createAccessToken(long payload, String role) {
        Map<String, Object> claims = Map.of("userId", payload, "role", role);
        return createToken(claims, accessTokenValidTime);
    }

    private Jws<Claims> parsingToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token);
    }

    public Claims getClaims(String token) {
        return parsingToken(token).getBody();
    }

    public void validateAccessToken(String accessToken) {
        try {
            parsingToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

}
