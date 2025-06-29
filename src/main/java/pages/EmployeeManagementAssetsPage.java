package pages;

import base.APIControlActions;
import io.restassured.response.Response;

public class EmployeeManagementAssetsPage extends APIControlActions {

    public int nextAvailableVendorId() {
        Response response = executeGetRequest("/api/assets/vendors");
        int numberOfVendorObject = response.jsonPath().getList("data").size();
        return numberOfVendorObject + 1;
    }

    public Response createVendor(String payload) {
        setPayload(payload);
        return executePostRequest("/api/assets/vendors");
    }


}
