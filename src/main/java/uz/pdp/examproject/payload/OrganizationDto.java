package uz.pdp.examproject.payload;

import lombok.Data;

@Data
public class OrganizationDto {
    private String name;
    private Integer regionId;
    private Integer parentId;
}
