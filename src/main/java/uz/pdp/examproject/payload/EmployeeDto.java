package uz.pdp.examproject.payload;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String pinfl;
    private Date hireDate;
    private Integer organizationId;
}
