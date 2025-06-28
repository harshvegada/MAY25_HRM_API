package testScript;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FileConstants;
import entity.skillCreate.CreateSkillPayload;
import entity.skillDelete.DeletePayload;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.jsoup.helper.DataUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.QualificationsSkillPage;
import utility.GenerateData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyWidgetsCases extends BaseTest {

    @Test
    public void verifyWidgets() {
        List<String> expectedWidgetList = Arrays.asList("My Actions", "Quick Access", "Time At Work", "Employees on Leave Today", "Latest News", "Latest Documents", "Performance Quick Feedback", "Current Year's Leave Taken by Department", "Buzz Latest Posts", "Leave Taken on Each Day of the Week Over Time", "Leave Scheduled in Each Month", "Leave Taken on Each Calendar Month Over the Years", "Headcount by Location", "Annual Basic Payment by Location");

        DashboardPage dashboardPage = new DashboardPage();
        Response widgetsResponse = dashboardPage.getAllWidgets();

        Allure.step("Expected Widget List : " + expectedWidgetList);
        Assert.assertEquals(widgetsResponse.statusCode(), 200);

        List<String> actualWidgetList = widgetsResponse.jsonPath().getList("data.title");
        Allure.step("Actual Widget List : " + actualWidgetList);
        Assert.assertEquals(actualWidgetList, expectedWidgetList);
    }

    @Test
    public void verifySkillASCOrder() {
        QualificationsSkillPage qualificationsSkillPage = new QualificationsSkillPage();
        String limit = "15";
        String description = "description";
        String sortingOrder = "DESC";
        Response response = qualificationsSkillPage.getAllSkill(limit, description, sortingOrder);
        Allure.step("Get All Skill based on " + limit + ", " + description + ", " + sortingOrder);
        Assert.assertEquals(Integer.parseInt(limit), response.jsonPath().getList("data").size(), "Query param functional breaking..");

        Response rawResponse = qualificationsSkillPage.getAllSkill();
        List<String> expectedList = rawResponse.jsonPath().getList("data.description");
        Allure.step("Expected Description List : " + expectedList);

        if (sortingOrder.equals("ASC")) {
            Collections.sort(expectedList);//ASC
            Assert.assertEquals(expectedList, response.jsonPath().getList("data.description"), "Name Field Not Same");
        } else {
            Collections.sort(expectedList, Collections.reverseOrder());
            Assert.assertEquals(expectedList, response.jsonPath().getList("data.description"), "Name Field Not Same");
        }

    }

    @Test
    public void createSkillAndVerifyInSame() throws JsonProcessingException {
        QualificationsSkillPage qualificationsSkillPage = new QualificationsSkillPage();

        CreateSkillPayload createSkillPayload = CreateSkillPayload.builder().name(GenerateData.getProgrammingLanguageName()).description(GenerateData.getSomeDescription()).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(createSkillPayload);
        Allure.step("Create Skill payload : " + payload);

        //Create Skill
        Response creatSkillResponse = qualificationsSkillPage.createSkill(payload);
        Assert.assertTrue(qualificationsSkillPage.validateSchema(FileConstants.CREATE_SKILL_SCHEMA, creatSkillResponse.asString()), "Create Skill Schema mis-match");

        Assert.assertEquals(creatSkillResponse.statusCode(), 201);
        String skillID = creatSkillResponse.jsonPath().getString("data.id");
        String skillDescription = creatSkillResponse.jsonPath().getString("data.description");
        String skillName = creatSkillResponse.jsonPath().getString("data.name");

        Allure.step("Skill ID : " + skillID);
        Allure.step("Skill Description : " + skillDescription);
        Allure.step("Skill Name : " + skillName);

        Response getAllSkillResponse = qualificationsSkillPage.getAllSkill();
        Assert.assertTrue(qualificationsSkillPage.validateSchema(FileConstants.GET_SKILL_SCHEMA, getAllSkillResponse.asString()), "Get All Skill Response payload Schema changes");

//        Option 1
//        boolean flag = false;
//        for (int i = 0; i < getAllSkillResponse.jsonPath().getList("data").size(); i++) {
//            if (getAllSkillResponse.jsonPath().getString("data[" + i + "].name").equals(skillName) &&
//                    getAllSkillResponse.jsonPath().getString("data[" + i + "].id").equals(skillID) &&
//                    getAllSkillResponse.jsonPath().getString("data[" + i + "].description").equals(skillDescription)
//            ) {
//                flag = true;
//            }
//        }
//        Assert.assertTrue(flag, "Skill is not getting created...");

//        Option 2
//        List<String> skillIdList = getAllSkillResponse.jsonPath().getList("data.id");
//        Optional<String> skillIDList = skillIdList.stream().filter(ele -> ele.equals(skillID)).findAny();
//        Assert.assertTrue(skillIDList.isPresent(), "Skill is not getting created...");

//        Option 3
        boolean flag = getAllSkillResponse.jsonPath().getList("data").stream()
                .filter(obj -> obj instanceof Map)
                .map(obj -> (Map<String, Object>) obj) //Type casting
                .anyMatch(skill ->
                        skillName.equals(skill.get("name")) &&
                                skillID.equals(skill.get("id")) &&
                                skillDescription.equals(skill.get("description"))
                );
        Assert.assertTrue(flag, "Skill is not getting created...");
        Allure.step(skillID + " match in skill");


        CreateSkillPayload updatePayload = CreateSkillPayload.builder()
                .name(GenerateData.getProgrammingLanguageName())
                .description(GenerateData.getSomeDescription()).build();

        //Serialization
        String updatedPayload = objectMapper.writeValueAsString(updatePayload);
        Allure.step("Updated Skill with : " + updatedPayload);
        Response updatedSkillResponse = qualificationsSkillPage.updateSkill(skillID, updatedPayload);
        Assert.assertTrue(qualificationsSkillPage.validateSchema(FileConstants.UPDATE_SKILL_SCHEMA, updatedSkillResponse.asString()), "Update Schema Response payload change");

        long seconds = updatedSkillResponse.timeIn(TimeUnit.SECONDS);
        Assert.assertTrue(seconds < 5, "");
        Assert.assertEquals(updatedSkillResponse.jsonPath().getString("messages.success"), "Successfully Saved", "Update Skill Details Failed");

        List<String> deleteSkillIdList = Arrays.asList(skillID);
        DeletePayload deletePayload = DeletePayload.builder().data(deleteSkillIdList).build();
        String deletePayloadString = objectMapper.writeValueAsString(deletePayload);
        Allure.step("Deleting the Skill with payload : " + deletePayloadString);
        Response deleteSkillResponse = qualificationsSkillPage.deleteSkill(deletePayloadString);
        Assert.assertEquals(deleteSkillResponse.statusCode(), 204, "Unable to delete skill");
    }

}
