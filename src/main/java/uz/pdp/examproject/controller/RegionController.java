package uz.pdp.examproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.examproject.payload.RegionDto;
import uz.pdp.examproject.service.RegionService;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping
    HttpEntity<?> addRegion(@RequestBody RegionDto regionDto){
        return regionService.addRegion(regionDto);
    }

    @GetMapping
    public HttpEntity<?> getAllRegion(){
        return regionService.getAllRegion();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getRegion(@PathVariable Integer id){
        return regionService.getRegion(id);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> updateRegion(@PathVariable Integer id, @RequestBody RegionDto regionDto){
        return regionService.updateRegion(id,regionDto);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deletedRegion(@PathVariable Integer id){
        return regionService.deleteRegion(id);
    }
}
