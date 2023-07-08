package uz.pdp.examproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.examproject.entity.ApiResponse;
import uz.pdp.examproject.entity.CalculationTable;
import uz.pdp.examproject.entity.Employee;
import uz.pdp.examproject.entity.Organization;
import uz.pdp.examproject.entity.enums.CalculationType;
import uz.pdp.examproject.exception.DataNotFoundException;
import uz.pdp.examproject.payload.CalculationTableDto;
import uz.pdp.examproject.payload.taskDto.Task2Dto;
import uz.pdp.examproject.payload.taskDto.Task3Dto;
import uz.pdp.examproject.payload.taskDto.Task4Dto;
import uz.pdp.examproject.payload.taskDto.Task5Dto;
import uz.pdp.examproject.repository.CalculationTableRepository;
import uz.pdp.examproject.repository.EmployeeRepository;
import uz.pdp.examproject.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalculationTableService {
    private final CalculationTableRepository calculationTableRepository;
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    public ApiResponse addCalculationTable(CalculationTableDto calculationTableDto) {
        Employee employee = employeeRepository.findById(calculationTableDto.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("No employee was found with this id"));

        Organization organization = organizationRepository.findById(calculationTableDto.getOrganizationId())
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));

        CalculationTable calculationTable = CalculationTable.builder()
                .organization(organization)
                .amount(calculationTableDto.getAmount())
                .rate(calculationTableDto.getRate())
                .date(calculationTableDto.getDate())
                .employee(employee)
                .calculationType(CalculationType.valueOf(calculationTableDto.getCalculationType()))
                .build();
        calculationTableRepository.save(calculationTable);
        return new ApiResponse("Calculation table  created",true);
    }

    public HttpEntity<?> getAll() {
        List<CalculationTable> calculationTableList = calculationTableRepository.findAll();
        return ResponseEntity.ok(calculationTableList);
    }

    public HttpEntity<?> getCalculationTable(Integer id) {
        CalculationTable calculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Calculation table not found"));
        return ResponseEntity.ok(calculationTable);
    }

    public ApiResponse update(Integer id, CalculationTableDto calculationTableDto) {
        CalculationTable calculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Calculation table not found " +id));

        Employee employee = employeeRepository.findById(calculationTableDto.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("No employee was found with this id"));

        Organization organization = organizationRepository.findById(calculationTableDto.getOrganizationId())
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));


        calculationTable.setCalculationType(CalculationType.valueOf(calculationTableDto.getCalculationType()));
        calculationTable.setOrganization(organization);
        calculationTable.setDate(calculationTableDto.getDate());
        calculationTable.setRate(calculationTableDto.getRate());
        calculationTable.setAmount(calculationTableDto.getAmount());
        calculationTable.setEmployee(employee);

        calculationTableRepository.save(calculationTable);
        return new ApiResponse("Calculation table updated successfully",true);
    }

    public ApiResponse deletedCalculationTable(Integer id) {
        CalculationTable calculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Calculation table not found " + id));
        calculationTableRepository.delete(calculationTable);
        return new ApiResponse("Calculation table deleted successfully",true);
    }

    public HttpEntity<?> Task2(Integer n, String date) {
        List<Task2Dto> totalRateAndPinfl = calculationTableRepository.getTotalRateAndPinfl(n, date);
        return ResponseEntity.ok(totalRateAndPinfl);
    }

    public HttpEntity<?> task3(String date) {
        List<Task3Dto> totalOrganizationNumberAndTotalMonthlySalary =
                calculationTableRepository.getTotalOrganizationNumberAndTotalMonthlySalary(date);
        return ResponseEntity.ok(totalOrganizationNumberAndTotalMonthlySalary);
    }

    public HttpEntity<?> task4(String date, Integer organizationId) {
        List<Task4Dto> averageSalaryAndEmployeeOrganization =
                calculationTableRepository.getAverageSalaryAndEmployeeOrganization(organizationId, date);
        return ResponseEntity.ok(averageSalaryAndEmployeeOrganization);
    }

    public HttpEntity<?> task5(String date) {
        List<Task5Dto> employeeAndSalaryAndVacation = calculationTableRepository.getEmployeeAndSalaryAndVacation(date);
        return ResponseEntity.ok(employeeAndSalaryAndVacation);
    }
}
