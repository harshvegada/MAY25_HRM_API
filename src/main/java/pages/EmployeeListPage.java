package pages;

import base.APIControlActions;
import io.restassured.response.Response;

public class EmployeeListPage extends APIControlActions {

    public Response createEmployee(String payload){
        setPayload(payload);
        return executePostRequest("/api/wizard");
    }

}
