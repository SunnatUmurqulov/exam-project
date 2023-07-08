package uz.pdp.examproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.examproject.entity.ApiResponse;
import uz.pdp.examproject.payload.OrganizationDto;
import uz.pdp.examproject.service.OrganizationService;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public HttpEntity<?> addOrganization(@RequestBody OrganizationDto organizationDto){
        return organizationService.addOrganization(organizationDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return organizationService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOrganization(@PathVariable Integer id){
        return organizationService.getOrganization(id);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> update(@PathVariable Integer id, @RequestBody OrganizationDto organizationDto){
        return organizationService.update(id,organizationDto);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return organizationService.deleteOrganization(id);
    }
}
