package com.payhere.gagebu.template;

import static com.payhere.gagebu.fixture.EntityGeneratorUtil.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.gagebu.domain.record.model.Record;
import com.payhere.gagebu.domain.record.repository.RecordRepository;
import com.payhere.gagebu.domain.user.dto.UserRequest.UserLogin;
import com.payhere.gagebu.domain.user.model.User;
import com.payhere.gagebu.domain.user.repository.UserRepository;
import com.payhere.gagebu.domain.user.service.AuthService;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public abstract class IntegrationTest {

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    protected void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .apply(documentationConfiguration(provider))
            .apply(springSecurity())
            .alwaysDo(print())
            .build();
    }

    protected String getToken(String email, String password) {
        var dto = UserLogin.builder()
            .email(email)
            .password(password)
            .build();
        return authService.login(dto).accessToken();
    }

    protected RequestHeadersSnippet tokenRequestHeader() {
        return requestHeaders(
            headerWithName(AUTHORIZATION).description("JWT accessToken")
        );
    }

    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected User makeUserAndSave(String password) {
        return userRepository.save(makeUser(password));
    }

    protected Record makeRecordAndSave(User user) {
        return recordRepository.save(makeRecord(user));
    }
}
