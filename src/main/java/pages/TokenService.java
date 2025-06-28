package pages;

import base.APIControlActions;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenService extends APIControlActions {

    private String csrfToken;

    private void login() {

        Response response = given()
                .baseUri(BASE_URI)
                .filter(new AllureRestAssured())
                .accept(ContentType.HTML)
                .when()
                .get("/auth/login")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
        cookies = response.cookies();

        //Option 1 : Jsoup
        Document document = Jsoup.parse(response.asString());
        //Option 1.a : If we have ID
        csrfToken = document.getElementById("login__csrf_token").attr("value");
    }

    private void validateCredential(String userName, String password) {
        Map<String, String> formData = new HashMap<>();
        formData.put("login[_csrf_token]", csrfToken);
        formData.put("hdnUserTimeZoneOffset", "5.5");
        formData.put("txtUsername", userName);
        formData.put("txtPassword", password);

        Response response = given()
                .baseUri(BASE_URI)
                .filter(new AllureRestAssured())
                .contentType(ContentType.URLENC)
                .formParams(formData)
                .cookies(cookies)
                .when()
                .post("/auth/validateCredentials")
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
        cookies = response.cookies();
    }

    private void getAccessToken() {
        Response response = given()
                .filter(new AllureRestAssured())
                .accept(ContentType.JSON)
                .baseUri(BASE_URI)
                .cookies(cookies) //Validate Response Cookies
                .when()
                .get("/core/getLoggedInAccountToken")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);

        bearerToken = response.jsonPath().getString("token.access_token");
        Assert.assertNotNull(bearerToken,"Bearer Token is Null");
//        setToken(bearerToken);
    }

    public void getAccessTokenFor(String userName, String password) {
        //Option 1.2 : If we don't have ID
//        String csrfTokenUsingCSS = document.select("input[name='login[_csrf_token]']").attr("value");
//        System.out.println(csrfTokenUsingCSS);


//        Option 2 : Regular Expression
//        Pattern pattern = Pattern.compile("[a-z0-9]{32}");
//        Matcher matcher = pattern.matcher(response.asString());
//        String csrfTokenUsingRegEx = "";
//        if (matcher.find()) {
//            csrfTokenUsingRegEx = matcher.group();
//        }
//        System.out.println(csrfTokenUsingRegEx);
        login();
        validateCredential(userName, password);
        getAccessToken();
    }

}
