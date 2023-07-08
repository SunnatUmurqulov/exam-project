package uz.pdp.examproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.examproject.entity.ApiResponse;
import uz.pdp.examproject.entity.Region;
import uz.pdp.examproject.exception.DataNotFoundException;
import uz.pdp.examproject.payload.RegionDto;
import uz.pdp.examproject.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;
    public HttpEntity<?> addRegion(RegionDto regionDto) {
        boolean exists = regionRepository.existsByName(regionDto.getName());
        if (exists){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Such a region was previously introduced",false));
        }
        Region region = new Region();
        region.setName(regionDto.getName());
        regionRepository.save(region);
        return ResponseEntity.ok(new ApiResponse("Region added successfully",true));
    }

    public HttpEntity<?> getAllRegion() {
        List<Region> regionList = regionRepository.findAll();
        return ResponseEntity.ok(regionList);
    }

    public HttpEntity<?> getRegion(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));
        return ResponseEntity.ok(region);
    }

    public HttpEntity<?> updateRegion(Integer id, RegionDto regionDto) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));
        region.setName(regionDto.getName());
        regionRepository.save(region);
        return ResponseEntity.ok(new ApiResponse("Region edited successfully",true));
    }

    public HttpEntity<?> deleteRegion(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No region found with this id"));
        regionRepository.delete(region);
        return ResponseEntity.ok(new ApiResponse("Region deleted successfully",true));
    }
}
