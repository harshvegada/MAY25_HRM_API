package pages;

import base.APIControlActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.skillCreate.CreateSkillPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class QualificationsSkillPage extends APIControlActions {


    @Step("Get All skill with Limit {0}, fieldName {1} sorting type {2}")
    public Response getAllSkill(String limit, String fieldName, String sortingOrder) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("sortingFeild", fieldName);
        queryParams.put("sortingOrder", sortingOrder);
        setQueryParam(queryParams);
        return executeGetRequest("/api/skills");
    }

    @Step("Get All the Skills")
    public Response getAllSkill() {
        return executeGetRequest("/api/skills");
    }


    public Response createSkill() throws JsonProcessingException {
        CreateSkillPayload createSkillPayload = CreateSkillPayload.builder().name("").description("").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(createSkillPayload);
        setPayload(payload);
        return executePostRequest("/api/skills");
    }

    @Step("Creating the skill using payload {0}")
    public Response createSkill(String payload) {
        setPayload(payload);
        return executePostRequest("/api/skills");
    }

    @Step("Updating the skill for {0} with value {1}")
    public Response updateSkill(String skillID, String payload) {
        setPayload(payload);
        return executePatchRequest("/api/skills/" + skillID);
    }

    @Step("Deleting the skill using payload")
    public Response deleteSkill(String payload) {
        setPayload(payload);
        return executeDeleteRequest("/api/skills");
    }
}
