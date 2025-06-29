package testScript;

import base.ApplicationConfig;
import constants.FileConstants;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.TokenService;
import utility.PropertyUtils;

public class BaseTest {

    PropertyUtils propertyUtils = new PropertyUtils(FileConstants.CONFIG_PROPERTIES);

    @BeforeMethod
    public void setup() {
        TokenService service = new TokenService();
        service.getAccessTokenFor(propertyUtils.getValue(ApplicationConfig.getEnvironment() + ".userName"), propertyUtils.getValue(ApplicationConfig.getEnvironment() + ".password"));
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) {
        if (iTestResult.isSuccess()) {
            System.out.println("Test Case Passed : " + iTestResult.getMethod().getMethodName());
        } else {
            System.out.println("Test Case Failed : " + iTestResult.getMethod().getMethodName());
        }
    }

}
