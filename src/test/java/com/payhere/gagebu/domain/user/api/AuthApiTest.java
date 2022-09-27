package com.payhere.gagebu.domain.user.api;

import static com.payhere.gagebu.fixture.EntityGeneratorUtil.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.payhere.gagebu.domain.user.dto.UserRequest.UserCreate;
import com.payhere.gagebu.template.IntegrationTest;

class AuthApiTest extends IntegrationTest {

    @Nested
    @DisplayName("회원가입")
    class SignUpTest {

        private final String endpoint = "/api/v1/users";

        @Test
        @DisplayName("회원가입 - 이메일과 비밀번호 입력")
        void signUp() throws Exception {
            var password = UUID.randomUUID().toString();

            var user = makeUser(password);
            var dto = UserCreate.builder()
                .email(user.getEmail())
                .password(password)
                .build();

            mockMvc.perform(post(endpoint).content(toJson(dto))
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("user-signup",
                ResourceSnippetParameters.builder()
                    .tag("User API")
                    .summary("회원가입 API")
                    .description("회원가입을 요청하는 API 입니다.")
                    .requestSchema(Schema.schema("회원가입 요청"))
                    .responseSchema(Schema.schema("회원가입 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseHeaders(
                    headerWithName("Location").description("회원가입 된 유저 URI")
                ),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("로그인 비밀번호")
                ),
                responseFields(
                    fieldWithPath("id").description("생성된 유저 아이디")
                )
            );
        }
    }

    @Nested
    @DisplayName("로그인")
    class LoginTest {

        private final String endpoint = "/api/v1/auth/login";

        @Test
        @DisplayName("로그인 - 이메일과 비밀번호 입력")
        void login() throws Exception {
            String password = UUID.randomUUID().toString();
            var user = makeUserAndSave(password);

            var dto = UserCreate.builder()
                .email(user.getEmail())
                .password(password)
                .build();

            mockMvc.perform(post(endpoint).content(toJson(dto))
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("user-login",
                ResourceSnippetParameters.builder()
                    .tag("User API")
                    .summary("로그인 API")
                    .description("로그인을 요청하는 API 입니다.")
                    .requestSchema(Schema.schema("로그인 요청"))
                    .responseSchema(Schema.schema("로그인 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("로그인 비밀번호")
                ),
                responseFields(
                    fieldWithPath("accessToken").description("생성된 유저 액세스 토큰")
                )
            );
        }
    }

}
