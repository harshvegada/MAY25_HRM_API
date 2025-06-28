package entity.createEmployee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateEmployeeJsonPayload {
    public String endpoint;
    public String method;
    public Data data;
    public Params params;
}