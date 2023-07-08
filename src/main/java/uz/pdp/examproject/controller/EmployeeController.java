package uz.pdp.examproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.examproject.payload.EmployeeDto;
import uz.pdp.examproject.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public HttpEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.addEmployee(employeeDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getEmployee(@PathVariable Integer id){
        return employeeService.getEmployee(id);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDto employeeDto){
        return employeeService.update(id,employeeDto);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return employeeService.deleteEmployee(id);
    }
}
