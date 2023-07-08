package uz.pdp.examproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.examproject.entity.CalculationTable;
import uz.pdp.examproject.payload.taskDto.Task2Dto;
import uz.pdp.examproject.payload.taskDto.Task3Dto;
import uz.pdp.examproject.payload.taskDto.Task4Dto;
import uz.pdp.examproject.payload.taskDto.Task5Dto;

import java.util.List;
import java.util.Optional;

public interface CalculationTableRepository extends JpaRepository<CalculationTable, Integer> {


    @Query(value = "SELECT e.pinfl, SUM(ct.rate) as totalRate FROM calculation_table ct join employee e on e.id = ct.employee_id" +
            "            WHERE ct.rate > :n AND" +
            "            CONCAT(EXTRACT(YEAR FROM ct.date), '.', LPAD(CAST(EXTRACT(MONTH FROM ct.date) AS TEXT), 2, '0')) = :requestedMonth " +
            "group by e.pinfl",nativeQuery = true)
    List<Task2Dto> getTotalRateAndPinfl(@Param(value = "n") Integer n, @Param(value = "requestedMonth") String date);

    @Query(value = "SELECT  COUNT(DISTINCT o.id) AS totalOrganizationNumber, SUM(ct.amount) AS totalMonthlySalary\n" +
            "FROM calculation_table ct\n" +
            "         JOIN Employee e ON ct.employee_id = e.id\n" +
            "         JOIN Organization o ON ct.organization_id = o.id\n" +
            "         JOIN Region r ON o.region_id = r.id\n" +
            "WHERE CONCAT(EXTRACT(YEAR FROM ct.date), '.', LPAD(CAST(EXTRACT(MONTH FROM ct.date) AS TEXT), 2, '0')) = :date\n" +
            "GROUP BY e.pinfl",nativeQuery = true)
    List<Task3Dto> getTotalOrganizationNumberAndTotalMonthlySalary(@Param(value = "date") String date);

    @Query(value = "SELECT COALESCE(o.organization_id, 0),AVG(ct.amount) AS AverageSalary, e.id AS EmployeeId," +
            " e.first_name AS EmployeeFirstName, e.last_name as EmployeeLastName, e.hire_date as EmployeeHireDate,\n" +
            "      e.pinfl as EmployeePinfl, e.organization_id as EmployeeOrganizationId, o.id as OrganizationId," +
            "o.organization_id as ParentOrganizationId, o.region_id as OrganizationRegionId, o.name as OrganizationName FROM calculation_table ct\n" +
            "         JOIN Employee e ON ct.employee_id = e.id\n" +
            "         JOIN Organization o ON ct.organization_id = o.id\n" +
            "WHERE o.id = :organizationId\n" +
            "AND CONCAT(EXTRACT(YEAR FROM ct.date), '.', LPAD(CAST(EXTRACT(MONTH FROM ct.date) AS TEXT), 2, '0')) = :date\n" +
            "GROUP BY e.id, o.id;",nativeQuery = true)

    List<Task4Dto> getAverageSalaryAndEmployeeOrganization(@Param(value = "organizationId") Integer organizationId,
                                                           @Param(value = "date") String date);

    @Query(value = "SELECT e.id AS EmployeeId, e.first_name AS FirstName, e.last_name AS LastName,e.pinfl as EmployeePinfl,\n" +
            "       SUM(CASE WHEN ct.calculation_type = 'SALARY' THEN ct.amount ELSE 0 END) AS Salary,\n" +
            "            SUM(CASE WHEN ct.calculation_type = 'VACATION' THEN ct.amount ELSE 0 END) AS Vacation\n" +
            "            FROM calculation_table ct\n" +
            "            JOIN employee e on ct.employee_id = e.id\n" +
            "            WHERE  CONCAT(EXTRACT(YEAR FROM ct.date), '.', LPAD(CAST(EXTRACT(MONTH FROM ct.date) AS TEXT), 2, '0')) = :date\n" +
            "            GROUP BY e.first_name, e.last_name, ct.calculation_type, e.pinfl, e.id",nativeQuery = true)

    List<Task5Dto> getEmployeeAndSalaryAndVacation(@Param(value = "date") String date);


}
