package uz.pdp.examproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.examproject.entity.ApiResponse;
import uz.pdp.examproject.entity.Employee;
import uz.pdp.examproject.entity.Organization;
import uz.pdp.examproject.exception.DataNotFoundException;
import uz.pdp.examproject.payload.EmployeeDto;
import uz.pdp.examproject.repository.EmployeeRepository;
import uz.pdp.examproject.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    public HttpEntity<?> addEmployee(EmployeeDto employeeDto) {
        boolean exists = employeeRepository.existsByPinflAndOrganizationId(employeeDto.getPinfl(), employeeDto.getOrganizationId());
        if (exists){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Such an employee exists",false));
        }

        Organization organization = organizationRepository.findById(employeeDto.getOrganizationId())
                .orElseThrow(() -> new DataNotFoundException("Organization does not exist"));

        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .hireDate(employeeDto.getHireDate())
                .organization(organization)
                .pinfl(employeeDto.getPinfl())
                .build();
        employeeRepository.save(employee);
        return ResponseEntity.ok(new ApiResponse("Employee successfully added",true));
    }

    public HttpEntity<?> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return ResponseEntity.ok(employeeList);
    }

    public HttpEntity<?> getEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));
        return ResponseEntity.ok(employee);
    }

    public HttpEntity<?> update(Integer id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));

        Organization organization = organizationRepository.findById(employeeDto.getOrganizationId())
                .orElseThrow(() -> new DataNotFoundException("Organization not found"));

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setHireDate(employeeDto.getHireDate());
        employee.setPinfl(employeeDto.getPinfl());
        employee.setOrganization(organization);

        employeeRepository.save(employee);
        return ResponseEntity.ok(new ApiResponse("Employee updated successfully",true));
    }

    public HttpEntity<?> deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No employee was found with this id"));
        employeeRepository.delete(employee);
        return ResponseEntity.ok(new ApiResponse("Employee was successfully deleted",true));
    }
}
