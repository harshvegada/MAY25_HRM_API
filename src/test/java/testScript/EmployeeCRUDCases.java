package testScript;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.FileConstants;
import entity.createEmployee.CreateEmployeeJsonPayload;
import entity.createEmployee.Data;
import entity.createEmployee.Params;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeListPage;
import utility.GenerateData;

import java.util.Arrays;

public class EmployeeCRUDCases extends BaseTest {

    @Test
    public void createEmployeeTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String firstName = GenerateData.getFirstName();
        String lastName = GenerateData.getLastName();
        String middleName = GenerateData.getMiddleName();
        String joiningDate = GenerateData.getFuture();
        String userName = GenerateData.getUserName();
        String password = GenerateData.getRandomString();

        Data primaryInformation = Data.builder().firstName(firstName).middleName(middleName).lastName(lastName).chkLogin(true).locationId("1").joinedDate(joiningDate).autoGenerateEmployeeId(true).initiatePreboarding(false).employeeId(null).userName(userName).userPassword(password).rePassword(password).status("Enabled").essRoleId("2").supervisorRoleId("3").adminRoleId("1").build();
        Params params = Params.builder().build();
        CreateEmployeeJsonPayload firstJsonObject = CreateEmployeeJsonPayload.builder().data(primaryInformation).params(params).method("POST").endpoint("employees").build();

        ObjectNode firstJSONObject = mapper.valueToTree(firstJsonObject);
        ObjectNode data = (ObjectNode) firstJSONObject.get("data");
        data.putNull("employeeId");


        Data personInformation = Data.builder().firstName(firstName).middleName(middleName).lastName(lastName).emp_gender(null).emp_marital_status(null).nation_code("82").emp_birthday(null).emp_dri_lice_exp_date(null).build();
        CreateEmployeeJsonPayload secondsJsonObject = CreateEmployeeJsonPayload.builder().data(personInformation).params(params).method("PATCH").endpoint("employees/<EMPNUMBER>").build();

        ObjectNode secondJSONObject = mapper.valueToTree(secondsJsonObject);
        ObjectNode dataSecondJson = (ObjectNode) secondJSONObject.get("data");
        dataSecondJson.putNull("emp_marital_status");
        dataSecondJson.putNull("emp_gender");
        dataSecondJson.putNull("emp_birthday");
        dataSecondJson.putNull("emp_dri_lice_exp_date");

        Data jobInformation = Data.builder().joined_date(joiningDate).probation_end_date(null).date_of_permanency(null).job_title_id("12").employment_status_id("3").job_category_id("").subunit_id("").location_id("1").work_schedule_id("2").cost_centre_id(null).has_contract_details("1").contract_start_date(null).contract_end_date(null).comment("").effective_date(joiningDate).event_id("1").build();

        CreateEmployeeJsonPayload thirdJsonObject = CreateEmployeeJsonPayload.builder().data(jobInformation).params(params).method("PATCH").endpoint("employees/<EMPNUMBER>/job").build();

        ObjectNode thirdJSONObject = mapper.valueToTree(thirdJsonObject);
        ObjectNode jobData = (ObjectNode) thirdJSONObject.get("data");
        jobData.putNull("probation_end_date");
        jobData.putNull("date_of_permanency");
        jobData.putNull("cost_centre_id");
        jobData.putNull("contract_start_date");
        jobData.putNull("contract_end_date");

        Data subData = Data.builder().eventTemplate("2").dueDate(joiningDate).owners(Arrays.asList("29", "38")).build();
        Data mainData = Data.builder().data(subData).build();

        CreateEmployeeJsonPayload fourthJsonObject = CreateEmployeeJsonPayload.builder().endpoint("employeeOnboarding/<EMPNUMBER>/create").method("POST").params(params).data(mainData).build();


        String first = mapper.writeValueAsString(firstJSONObject);
        String second = mapper.writeValueAsString(secondJSONObject);
        String third = mapper.writeValueAsString(thirdJSONObject);
        String fourth = mapper.writeValueAsString(fourthJsonObject);

        String finalPayload = String.valueOf(Arrays.asList(first, second, third, fourth));

        EmployeeListPage employeeListPage = new EmployeeListPage();
        Response createEmployeeResponse = employeeListPage.createEmployee(finalPayload);

        Assert.assertEquals(createEmployeeResponse.statusCode(), 200);
        Assert.assertTrue(employeeListPage.validateSchema(FileConstants.CREATE_EMPLOYEE_SCHEMA, createEmployeeResponse.asString()), "Employee Schema Failed");

        String employeeId = createEmployeeResponse.jsonPath().getString("data.empNumber");

        System.out.println(employeeId);

    }
}
