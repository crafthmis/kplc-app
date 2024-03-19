package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.County;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountyController extends BaseController {

    @GetMapping("/counties")
    public ResponseEntity<Object> getCounties() {
        return countyService.getCounties();
    }

    @GetMapping("/county/{id}")
    public ResponseEntity<Object> getCounty(@PathVariable Long id) {
        return countyService.getCounty(id);
    }


    @GetMapping("/county/{id}/constituencies")
    public ResponseEntity<Object> getCountyConstituencies(@PathVariable Long id) {
        return countyService.getCountyConstituencies(id);
    }


    @PostMapping("/county/add")
    public ResponseEntity<Object> addNewCounty(@RequestBody County county) {
        return countyService.createNewCounty(county);
    }

    @DeleteMapping("/county/{id}")
    public ResponseEntity<Object> deleteCounty(@PathVariable Long id) {
        return countyService.deleteCounty(id);
    }

    @PutMapping("/county/{id}")
    public ResponseEntity<Object> updateCounty(@PathVariable("id") Long id, @RequestParam(required = false) String name) {
        return countyService.updateCounty(id, name);
    }
}
