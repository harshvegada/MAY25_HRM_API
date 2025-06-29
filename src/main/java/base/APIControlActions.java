package base;

import constants.FileConstants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pages.TokenService;
import utility.PropertyUtils;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIControlActions {

    protected static ThreadLocal<Map<String, String>> cookiesThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<RequestSpecification> requestSpecificationThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<String> bearerTokenThreadLocal = new ThreadLocal<>(); //driver

    protected static String BASE_URI = PropertyUtils.getValue(FileConstants.CONFIG_PROPERTIES, ApplicationConfig.getEnvironment() + ".url");

    protected void setHeaders(Map<String, String> headers) {
        buildRequestSpecBuilder();
        requestSpecificationThreadLocal.get().headers(headers);
    }

    protected void setFormData(Map<String, String> formData) {
        buildRequestSpecBuilder();
        requestSpecificationThreadLocal.get().formParams(formData);
    }

    protected void setQueryParam(Map<String, String> queryParams) {
        buildRequestSpecBuilder();
        requestSpecificationThreadLocal.get().queryParams(queryParams);
    }

    protected void setPayload(String payloadBody) {
        buildRequestSpecBuilder();
        requestSpecificationThreadLocal.get().body(payloadBody);
    }

    private void buildRequestSpecBuilder() {
        if (requestSpecificationThreadLocal.get() == null)
            requestSpecificationThreadLocal.set(given());
    }

    protected Response executeGetRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecificationThreadLocal.get())
                .baseUri(BASE_URI)
                .cookies(cookiesThreadLocal.get())
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .header("Authorization", "Bearer " + bearerTokenThreadLocal.get())
                .when().get(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecificationThreadLocal.set(null);
        return response;
    }

    protected Response executePostRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecificationThreadLocal.get())
                .baseUri(BASE_URI)
                .cookies(cookiesThreadLocal.get())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerTokenThreadLocal.get())
                .when().post(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecificationThreadLocal.set(null);
        return response;
    }

    protected Response executePatchRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecificationThreadLocal.get())
                .baseUri(BASE_URI)
                .cookies(cookiesThreadLocal.get())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerTokenThreadLocal.get())
                .when().patch(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecificationThreadLocal.set(null);
        return response;
    }

    protected Response executeDeleteRequest(String endPoint) {
        buildRequestSpecBuilder();
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all().spec(requestSpecificationThreadLocal.get())
                .baseUri(BASE_URI)
                .cookies(cookiesThreadLocal.get())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerTokenThreadLocal.get())
                .when().delete(endPoint).then().extract().response();
//        if (response.statusCode() > 300) {
//            TokenService tokenService = new TokenService();
//            tokenService.getAccessTokenFor("","");
//            executeGetRequest(endPoint);
//        }
        requestSpecificationThreadLocal.set(null);
        return response;
    }

    public boolean validateSchema(String filePath, String responsePayload) {
        return JsonSchemaValidator.matchesJsonSchema(new File(filePath)).matches(responsePayload);
    }

}
