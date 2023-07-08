package uz.pdp.examproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.examproject.entity.ApiResponse;
import uz.pdp.examproject.payload.CalculationTableDto;
import uz.pdp.examproject.service.CalculationTableService;

@RestController
@RequestMapping("/api/calculation-table")
@RequiredArgsConstructor
public class CalculationTableController {
    private final CalculationTableService calculationTableService;

    @PostMapping
    public ApiResponse addCalculationTable(@RequestBody CalculationTableDto calculationTableDto){
        return calculationTableService.addCalculationTable(calculationTableDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return calculationTableService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCalculationTable(@PathVariable Integer id){
        return calculationTableService.getCalculationTable(id);
    }

    @PutMapping("/edit/{id}")
    public ApiResponse update(@PathVariable Integer id, @RequestBody CalculationTableDto calculationTableDto){
        return calculationTableService.update(id,calculationTableDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleted(@PathVariable Integer id){
        return calculationTableService.deletedCalculationTable(id);
    }

    @GetMapping("/total-work-rate")
    public HttpEntity<?> Task2(@RequestParam Integer n, @RequestParam String date){
        return calculationTableService.Task2(n,date);
    }

    @GetMapping("/region-wise-summary")
    public HttpEntity<?> task3(@RequestParam String date){
        return calculationTableService.task3(date);
    }

    @GetMapping("/employees-summary")
    public HttpEntity<?> task4(@RequestParam String date,@RequestParam Integer organizationId){
        return calculationTableService.task4(date,organizationId);
    }

    @GetMapping("/salary-vacation-info")
    public HttpEntity<?> task5(@RequestParam String date){
        return calculationTableService.task5(date);
    }
}
