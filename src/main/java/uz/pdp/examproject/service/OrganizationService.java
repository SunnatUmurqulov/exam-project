package uz.pdp.examproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.examproject.entity.ApiResponse;
import uz.pdp.examproject.entity.Organization;
import uz.pdp.examproject.entity.Region;
import uz.pdp.examproject.exception.DataNotFoundException;
import uz.pdp.examproject.payload.OrganizationDto;
import uz.pdp.examproject.repository.OrganizationRepository;
import uz.pdp.examproject.repository.RegionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final RegionRepository regionRepository;
    public HttpEntity<?> addOrganization(OrganizationDto organizationDto) {
        boolean exists = organizationRepository.existsByName(organizationDto.getName());
        if (exists){
            return ResponseEntity.status(HttpStatus.FOUND).
                    body(new ApiResponse("Such an organization exists",false));
        }

        Region region = regionRepository.findById(organizationDto.getRegionId())
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));

        Organization organization = new Organization();
        organization.setName(organizationDto.getName());
        if (organizationDto.getParentId() != null){
            Organization optionalOrganization = organizationRepository.findById(organizationDto.getParentId())
                    .orElseThrow(() -> new DataNotFoundException("No such parentId found"));
            organization.setOrganization(optionalOrganization);
        }
        organization.setRegion(region);
        organizationRepository.save(organization);
        return ResponseEntity.ok(new ApiResponse("Organization has been successfully added",true));
    }

    public HttpEntity<?> getAll() {
        List<Organization> organizationList = organizationRepository.findAll();
        return ResponseEntity.ok(organizationList);
    }

    public HttpEntity<?> getOrganization(Integer id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Organization not found"));
        return ResponseEntity.ok(organization);
    }

    public HttpEntity<?> update(Integer id, OrganizationDto organizationDto) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No organization found with this id"));
        Region region = regionRepository.findById(organizationDto.getRegionId())
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));
        organization.setRegion(region);
        organization.setName(organizationDto.getName());
        if (organizationDto.getParentId() != null){
            Organization optionalOrganization = organizationRepository.findById(organizationDto.getParentId())
                    .orElseThrow(() -> new DataNotFoundException("Parent organization not found"));
            organization.setOrganization(optionalOrganization);
        }
        organizationRepository.save(organization);
        return ResponseEntity.ok(new ApiResponse("Organization edited successfully",true));
    }

    public HttpEntity<?> deleteOrganization(Integer id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No organization found with this id"));
        organizationRepository.delete(organization);
        return ResponseEntity.ok(new ApiResponse("Organization deleted successfully",true));
    }
}
