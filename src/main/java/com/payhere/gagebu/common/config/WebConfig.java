package com.payhere.gagebu.common.config;

import static org.springframework.http.HttpHeaders.*;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.payhere.gagebu.common.annotation.LoginUserArgumentResolver;
import com.payhere.gagebu.common.config.property.CorsConfigProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsConfigProperties.class)
public class WebConfig implements WebMvcConfigurer {

    private final CorsConfigProperties corsConfigProperties;

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
            .setCacheControl(CacheControl.noCache());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping(corsConfigProperties.api())
            .allowedOrigins(corsConfigProperties.origin())
            .allowedMethods(corsConfigProperties.method())
            .allowCredentials(true).maxAge(3600)
            .exposedHeaders(LOCATION);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

}
