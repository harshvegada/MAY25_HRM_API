package testScript;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FileConstants;
import constants.StatusCode;
import entity.creatVendor.CreateVendorPayload;
import entity.creatVendor.responseCreateVendor.CreateVendorResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeManagementAssetsPage;
import utility.GenerateData;

public class VendorTestCases extends BaseTest {

    @Test
    public void createVendor() throws JsonProcessingException {
        EmployeeManagementAssetsPage employeeManagementAssetsPage = new EmployeeManagementAssetsPage();
        int nextVendorId = employeeManagementAssetsPage.nextAvailableVendorId();


        //Seriliazation
        CreateVendorPayload createVendorPayload = CreateVendorPayload.builder().vendorCode("0" + nextVendorId).vendorName(GenerateData.getCompanyName()).address(GenerateData.getAddress()).contactNo(GenerateData.getPhoneNumber()).email(GenerateData.getFirstName() + "@gmail.com").website(GenerateData.getWebSite()).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(createVendorPayload);

        Response createVendorResponse = employeeManagementAssetsPage.createVendor(payload);
        Assert.assertEquals(createVendorResponse.statusCode(), StatusCode.CREATED);

        //Schema validation
        Assert.assertTrue(employeeManagementAssetsPage.validateSchema(FileConstants.CREATE_VENDOR_SCHEMA, createVendorResponse.asString()));


        //De-Serilization
        //Using RestAssured
        CreateVendorResponse createVendorResDeSer = createVendorResponse.as(CreateVendorResponse.class);
        System.out.println(createVendorResDeSer.getData().getAddress());
        System.out.println(createVendorResDeSer.getData().getVendorName());

        //Using Object Mapper
        CreateVendorResponse mapperDerSer = objectMapper.readValue(createVendorResponse.asString(), CreateVendorResponse.class);
        System.out.println(mapperDerSer.getData().getAddress());
        System.out.println(mapperDerSer.getData().getVendorName());

    }

}
