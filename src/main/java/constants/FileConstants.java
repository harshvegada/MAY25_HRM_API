package constants;

import java.io.File;

public class FileConstants {

    private FileConstants() {
    }

    //Config File Path
    public static final String CONFIG_PROPERTIES = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "config" + File.separator + "config.properties";

    //    Skill Page
    public static final String SKILL_SCHEMA_FILE_PATH = System.getProperty("user.dir") +
            File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "schema" + File.separator + "skill";

    public static final String CREATE_SKILL_SCHEMA = SKILL_SCHEMA_FILE_PATH + File.separator + "createSkillSchema.json";
    public static final String UPDATE_SKILL_SCHEMA = SKILL_SCHEMA_FILE_PATH + File.separator + "updateSkillSchema.json";
    public static final String GET_SKILL_SCHEMA = SKILL_SCHEMA_FILE_PATH + File.separator + "getSkillSchema.json";


    //    Employee List Page
    public static final String EMPLOYEE_LIST_SCHEMA_FILE_PATH = System.getProperty("user.dir") +
            File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "schema" + File.separator + "employeeList";

    public static final String CREATE_EMPLOYEE_SCHEMA = EMPLOYEE_LIST_SCHEMA_FILE_PATH + File.separator + "createEmployeeSchema.json";

    //    Vendor List Page
    public static final String VENDOR_LIST_SCHEMA_FILE_PATH = System.getProperty("user.dir") +
            File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "schema" + File.separator + "vendor";

    public static final String CREATE_VENDOR_SCHEMA = VENDOR_LIST_SCHEMA_FILE_PATH + File.separator + "createVendorSchema.json";




}
