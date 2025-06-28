package pages;

import base.APIControlActions;
import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class DashboardPage extends APIControlActions {

    public Response getAllWidgets() {
        Allure.step("Get All Widget");
        return executeGetRequest("/api/dashboard/widgets");
    }


// Hybrid = Data Driven + Functional + Modular + POM

}
