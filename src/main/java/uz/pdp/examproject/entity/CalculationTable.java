package uz.pdp.examproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.examproject.entity.enums.CalculationType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CalculationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Employee employee;
    private double amount;
    private Integer rate;
    private Date date;
    @ManyToOne
    private Organization organization;
    @Enumerated(value = EnumType.STRING)
    private CalculationType calculationType;
}
