package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegionController extends BaseController {


    @GetMapping("/regions")
    public List<Region> getRegions() {
        return regionService.getRegions();
    }

    @GetMapping("/region/{id}/")
    public ResponseEntity<Object> getRegion(@PathVariable Long id) {
        return regionService.getRegion(id);
    }

    @GetMapping("/region/{id}/counties")
    public ResponseEntity<Object> getRegionCounties(@PathVariable Long id) {
        return regionService.getRegionCounties(id);
    }

    @PostMapping("/region/add")
    public ResponseEntity<Object> addNewRegion(@RequestBody Region region) {
        return regionService.createNewRegion(region);
    }

    @DeleteMapping("/region/{id}")
    public void deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
    }

    @PutMapping("/region/{id}")
    public void updateRegion(@PathVariable("id") Long id, @RequestParam(required = false) String name) {
        regionService.updateRegion(id, name);
    }

}
