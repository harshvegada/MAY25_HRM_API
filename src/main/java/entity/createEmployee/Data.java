package entity.createEmployee;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data{
    public String firstName;
    public String middleName;
    public String lastName;
    public Boolean chkLogin;
    public String locationId;
    public String joinedDate;
    public Boolean autoGenerateEmployeeId;
    public Boolean initiatePreboarding;
    public Object employeeId;
    public String userName;
    public String userPassword;
    public String rePassword;
    public String status;
    public String essRoleId;
    public String supervisorRoleId;
    public String adminRoleId;
    public Object emp_gender;
    public Object emp_marital_status;
    public String nation_code;
    public Object emp_birthday;
    public Object emp_dri_lice_exp_date;
    public String joined_date;
    public Object probation_end_date;
    public Object date_of_permanency;
    public String job_title_id;
    public String employment_status_id;
    public String job_category_id;
    public String subunit_id;
    public String location_id;
    public String work_schedule_id;
    public Object cost_centre_id;
    public String has_contract_details;
    public Object contract_start_date;
    public Object contract_end_date;
    public String comment;
    public String effective_date;
    public String event_id;
    public Data data;
    public String eventTemplate;
    public String dueDate;
    public List<String> owners;
}