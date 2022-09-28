package com.payhere.gagebu.domain.record.api;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordCreate;
import com.payhere.gagebu.domain.record.dto.RecordRequest.RecordEdit;
import com.payhere.gagebu.template.IntegrationTest;

@DisplayName("Record 통합테스트")
class RecordApiTest extends IntegrationTest {

    @Nested
    @DisplayName("거래내역 생성")
    class CreateRecordTest {

        private final String endpoint = "/api/v1/records";

        @Test
        @DisplayName("오늘 사용한 돈의 금액과 관련된 메모를 남길 수 있습니다")
        void createRecordTest() throws Exception {
            var password = "password";
            var user = makeUserAndSave(password);
            var token = getToken(user.getEmail(), password);

            var dto = RecordCreate.builder()
                .name("테스트 거래")
                .money(1_000_000)
                .memo("테스트 메모 입니다")
                .build();

            mockMvc.perform(post(endpoint).content(toJson(dto))
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("record-create",
                ResourceSnippetParameters.builder()
                    .tag("Record API")
                    .summary("거래내역 생성 API")
                    .description("거래내역을 생성하는 API 입니다.")
                    .requestSchema(Schema.schema("거래내역 생성 요청"))
                    .responseSchema(Schema.schema("거래내역 생성 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                responseHeaders(
                    headerWithName("Location").description("생성된 거래내역 URI")
                ),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("거래내역 이름"),
                    fieldWithPath("money").type(JsonFieldType.NUMBER).description("거래내역 금액"),
                    fieldWithPath("memo").type(JsonFieldType.STRING).description("거래내역 메모")
                ),
                responseFields(
                    fieldWithPath("id").description("생성된 거래내역 아이디")
                )
            );
        }

    }

    @Nested
    @DisplayName("거래내역 삭제")
    class DeleteRecordTest {

        private final String endpoint = "/api/v1/records/{recordId}";

        @Test
        @DisplayName("삭제를 원하는 내역은 삭제 할 수 있습니다.")
        void deleteRecordTest() throws Exception {
            var password = "password";
            var user = makeUserAndSave(password);
            var record = makeRecordAndSave(user);
            var token = getToken(user.getEmail(), password);

            mockMvc.perform(delete(endpoint, record.getId())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("record-delete",
                ResourceSnippetParameters.builder()
                    .tag("Record API")
                    .summary("거래내역 삭제 API")
                    .description("거래내역을 삭제하는 API 입니다.")
                    .requestSchema(Schema.schema("거래내역 삭제 요청"))
                    .responseSchema(Schema.schema("거래내역 삭제 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                pathParameters(
                    parameterWithName("recordId").description("거래내역 아이디")
                )
            );
        }
    }

    @Nested
    @DisplayName("거래내역 복구")
    class RestoreRecordTest {

        private final String endpoint = "/api/v1/records/{recordId}";

        @Test
        @DisplayName("삭제한 내역은 언제든지 다시 복구 할 수 있어야 합니다.")
        void restoreRecordTest() throws Exception {
            var password = "password";
            var user = makeUserAndSave(password);
            var record = makeRecordAndSave(user);
            var token = getToken(user.getEmail(), password);

            mockMvc.perform(put(endpoint, record.getId())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("record-restore",
                ResourceSnippetParameters.builder()
                    .tag("Record API")
                    .summary("거래내역 복구 API")
                    .description("거래내역을 복구하는 API 입니다.")
                    .requestSchema(Schema.schema("거래내역 복구 요청"))
                    .responseSchema(Schema.schema("거래내역 복구 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                pathParameters(
                    parameterWithName("recordId").description("거래내역 아이디")
                )
            );
        }
    }

    @Nested
    @DisplayName("거래내역 수정")
    class EditRecordTest {

        private final String endpoint = "/api/v1/records/{recordId}";

        @Test
        @DisplayName("수정을 원하는 내역은 금액과 메모를 수정 할 수 있습니다.")
        void editRecordTest() throws Exception {
            var password = "password";
            var user = makeUserAndSave(password);
            var record = makeRecordAndSave(user);
            var token = getToken(user.getEmail(), password);
            var dto = RecordEdit.builder()
                .money(1_000)
                .memo("수정된 메모")
                .build();

            mockMvc.perform(patch(endpoint, record.getId()).content(toJson(dto))
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("record-edit",
                ResourceSnippetParameters.builder()
                    .tag("Record API")
                    .summary("거래내역 수정 API")
                    .description("거래내역을 수정하는 API 입니다.")
                    .requestSchema(Schema.schema("거래내역 수정 요청"))
                    .responseSchema(Schema.schema("거래내역 수정 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                pathParameters(
                    parameterWithName("recordId").description("거래내역 아이디")
                ),
                requestFields(
                    fieldWithPath("money").type(JsonFieldType.NUMBER).description("거래내역 금액"),
                    fieldWithPath("memo").type(JsonFieldType.STRING).description("거래내역 메모")
                )
            );
        }
    }

    @Nested
    @DisplayName("거래내역 단건 조회")
    class GetRecordTest {

        private final String endpoint = "/api/v1/records/{recordId}";

        @Test
        @DisplayName("가계부에서 상세한 세부 내역을 볼 수 있습니다.")
        void getRecordTest() throws Exception {
            var password = "password";
            var user = makeUserAndSave(password);
            var record = makeRecordAndSave(user);
            var token = getToken(user.getEmail(), password);

            mockMvc.perform(get(endpoint, record.getId())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(record.getName()))
                .andExpect(jsonPath("$.money").value(record.getMoney()))
                .andExpect(jsonPath("$.memo").value(record.getMemo()))
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("record-findOne",
                ResourceSnippetParameters.builder()
                    .tag("Record API")
                    .summary("거래내역 단건 조회 API")
                    .description("거래내역 단건 조회 API 입니다.")
                    .requestSchema(Schema.schema("거래내역 단건조회 요청"))
                    .responseSchema(Schema.schema("거래내역 단건조회 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                pathParameters(
                    parameterWithName("recordId").description("거래내역 아이디")
                ),
                responseFields(
                    fieldWithPath("name").description("거래내역 이름"),
                    fieldWithPath("money").description("거래내역 금액"),
                    fieldWithPath("memo").description("거래내역 메모"),
                    fieldWithPath("createdAt").description("거래내역 생성 일자"),
                    fieldWithPath("updatedAt").description("거래내역 마지막 수정일자")
                )
            );
        }
    }

    @Nested
    @DisplayName("거래내역 다중 조회")
    class GetAllRecordTest {

        private final String endpoint = "/api/v1/records";

        @Test
        @DisplayName("이제까지 기록한 가계부 리스트를 볼 수 있습니다.")
        void getAllRecordTest() throws Exception {
            var password = "password";
            var user = makeUserAndSave(password);
            makeRecordAndSave(user);
            makeRecordAndSave(user);
            makeRecordAndSave(user);
            makeRecordAndSave(user);
            var token = getToken(user.getEmail(), password);

            mockMvc.perform(get(endpoint)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.records").exists())
                .andDo(documentation());
        }

        private RestDocumentationResultHandler documentation() {
            return MockMvcRestDocumentationWrapper.document("record-findAll",
                ResourceSnippetParameters.builder()
                    .tag("Record API")
                    .summary("거래내역 다중 조회 API")
                    .description("거래내역 다중 조회 API 입니다.")
                    .requestSchema(Schema.schema("거래내역 다중조회 요청"))
                    .responseSchema(Schema.schema("거래내역 다중조회 응답")),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                tokenRequestHeader(),
                responseFields(
                    fieldWithPath("records[].name").description("거래내역 이름"),
                    fieldWithPath("records[].money").description("거래내역 금액"),
                    fieldWithPath("records[].memo").description("거래내역 메모"),
                    fieldWithPath("records[].createdAt").description("거래내역 생성 일자"),
                    fieldWithPath("records[].updatedAt").description("거래내역 마지막 수정일자")
                )
            );
        }
    }
}
