package entity.creatVendor.responseCreateVendor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Data {
    public int id;
    public String vendorCode;
    public String vendorName;
    public String address;
    public String isDeleted;
    public String contactNo;
    public String email;
    public String website;
    public String created_at;
    public String updated_at;
}