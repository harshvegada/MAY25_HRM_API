package entity.creatVendor.responseCreateVendor;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meta{
    public int lastVendorId;
    public Permissions permissions;
}