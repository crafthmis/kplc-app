package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.Constituency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConstituencyController extends BaseController {

    @GetMapping("/constituencies")
    public ResponseEntity<Object> getConstituencies() {
        return constituencyService.getConstituencies();
    }

    @GetMapping("/constituency/{id}")
    public ResponseEntity<Object> getConstituency(@PathVariable Long id) {
        return constituencyService.getConstituency(id);
    }


    @GetMapping("/constituency/{id}/areas")
    public ResponseEntity<Object> getConstituencyAreas(@PathVariable Long id) {
        return constituencyService.getConstituencyAreas(id);
    }


    @PostMapping("/constituency/add")
    public ResponseEntity<Object> addNewConstituency(@RequestBody Constituency constituency) {
        return constituencyService.createNewConstituency(constituency);
    }

    @DeleteMapping("/constituency/{id}")
    public ResponseEntity<Object> deleteConstituency(@PathVariable Long id) {
        return constituencyService.deleteConstituency(id);
    }

    @PutMapping("/constituency/{id}")
    public ResponseEntity<Object> updateConstituency(@PathVariable("id") Long id, @RequestParam(required = false) String name) {
        return constituencyService.updateConstituency(id, name);
    }
}
