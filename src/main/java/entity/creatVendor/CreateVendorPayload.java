package entity.creatVendor;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVendorPayload{
    public String email;
    public String address;
    public String website;
    public String contactNo;
    public String vendorCode;
    public String vendorName;
}