package com.payhere.gagebu.security.config;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.payhere.gagebu.security.config.property.SecurityConfigProperties;
import com.payhere.gagebu.security.jwt.JwtAuthenticationFilter;
import com.payhere.gagebu.security.jwt.exception.handler.TokenExceptionHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class SecurityConfig {

    private final SecurityConfigProperties securityConfigProperties;

    private final TokenExceptionHandler tokenExceptionHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .antMatchers(securityConfigProperties.patterns().ignoring().get("ALL"))
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .antMatchers(securityConfigProperties.patterns().permitAll().get("ALL")).permitAll()
            .antMatchers(GET, securityConfigProperties.patterns().permitAll().get("GET")).permitAll()
            .antMatchers(POST, securityConfigProperties.patterns().permitAll().get("POST")).permitAll()
            .anyRequest().authenticated()
            .and()

            .cors().and()

            .csrf().disable()
            .httpBasic().disable()
            .rememberMe().disable()
            .logout().disable()
            .requestCache().disable()
            .formLogin().disable()
            .headers().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .exceptionHandling(e -> e
                .authenticationEntryPoint((req, res, authException) -> res.setStatus(SC_UNAUTHORIZED))
                .accessDeniedHandler((req, res, accessDeniedException) -> res.setStatus(SC_UNAUTHORIZED))
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(tokenExceptionHandler, JwtAuthenticationFilter.class)
        ;
        return http.build();
    }

}
