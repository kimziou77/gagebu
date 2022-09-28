package com.payhere.gagebu.domain.user.api;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.payhere.gagebu.template.IntegrationTest;

@DisplayName("User 통합테스트")
class UserApiTest extends IntegrationTest {

    @Nested
    @DisplayName("유저 단일 조회")
    class GetUserInfoTest {

        private final String endpoint = "/api/v1/users/{userId}";

        @Test
        @DisplayName("유저 정보 단일 조회")
        void getUserInfo() throws Exception {
            var password = "test-passwd";
            var user = makeUserAndSave(password);
            var token = getToken(user.getEmail(), password);

            mockMvc.perform(get(endpoint, user.getId())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("user-findOne",
                ResourceSnippetParameters.builder()
                    .tag("User API")
                    .summary("유저 조회 API")
                    .description("유저를 단일 조회하는 API 입니다.")
                    .requestSchema(Schema.schema("유저 단일 조회 요청"))
                    .responseSchema(Schema.schema("유저 단일 조회 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                pathParameters(
                    parameterWithName("userId").description("유저 아이디")
                ),
                responseFields(
                    fieldWithPath("userId").description("유저 아이디"),
                    fieldWithPath("email").description("유저 이메일")
                )
            );
        }

    }

}
