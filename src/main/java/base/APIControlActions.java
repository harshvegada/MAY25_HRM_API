package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pages.TokenService;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIControlActions {

    protected static Map<String, String> cookies;
    private static RequestSpecification requestSpecification;
    protected static String bearerToken; //driver

    protected static String BASE_URI = "https://virtus2027-trials7161.orangehrmlive.com";

    protected void setHeaders(Map<String, String> headers) {
        buildRequestSpecBuilder();
        requestSpecification.headers(headers);
    }

    protected void setFormData(Map<String, String> formData) {
        buildRequestSpecBuilder();
        requestSpecification.formParams(formData);
    }

    protected void setQueryParam(Map<String, String> queryParams) {
        buildRequestSpecBuilder();
        requestSpecification.queryParams(queryParams);
    }

    protected void setPayload(String payloadBody) {
        buildRequestSpecBuilder();
        requestSpecification.body(payloadBody);
    }

    private void buildRequestSpecBuilder() {
        if (requestSpecification == null)
            requestSpecification = given();
    }

    protected Response executeGetRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecification)
                .baseUri(BASE_URI)
                .cookies(cookies)
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .header("Authorization", "Bearer " + bearerToken)
                .when().get(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecification = null;
        return response;
    }

    protected Response executePostRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecification)
                .baseUri(BASE_URI)
                .cookies(cookies)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerToken)
                .when().post(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecification = null;
        return response;
    }

    protected Response executePatchRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecification)
                .baseUri(BASE_URI)
                .cookies(cookies)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerToken)
                .when().patch(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecification = null;
        return response;
    }

    protected Response executeDeleteRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecification)
                .baseUri(BASE_URI)
                .cookies(cookies)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerToken)
                .when().delete(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecification = null;
        return response;
    }

    public boolean validateSchema(String filePath, String responsePayload) {
        return JsonSchemaValidator.matchesJsonSchema(new File(filePath)).matches(responsePayload);
    }

}
