package uz.pdp.examproject.payload;

import lombok.Data;

import java.util.Date;

@Data
public class CalculationTableDto {
    private Integer employeeId;
    private double amount;
    private Integer rate;
    private Date date;
    private Integer organizationId;
    private String calculationType;
}
