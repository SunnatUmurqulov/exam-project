package uz.pdp.examproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.examproject.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByPinflAndOrganizationId(String pinfl, Integer organization_id);
}
