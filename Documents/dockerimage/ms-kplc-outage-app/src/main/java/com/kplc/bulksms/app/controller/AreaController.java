package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.Area;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AreaController extends BaseController {


    @GetMapping("/areas")
    public ResponseEntity<Object> geAreas() {
        return areaService.getAreas();
    }

    @GetMapping("/area/{id}")
    public ResponseEntity<Object> getArea(@PathVariable Long id) {
        return areaService.getArea(id);
    }

    @GetMapping("/area/{id}/contacts")
    public ResponseEntity<Object> getCountyConstituencies(@PathVariable Long id) {
        return areaService.getAreaContacts(id);
    }
    @PostMapping("/area/add")
    public ResponseEntity<Object> addNewArea(@RequestBody Area area) {
        return areaService.createNewArea(area);
    }

    @DeleteMapping("/area/{id}")
    public ResponseEntity<Object> deleteArea(@PathVariable Long id) {
        return areaService.deleteArea(id);
    }

    @PutMapping("/area/{id}")
    public ResponseEntity<Object> updateArea(@PathVariable("id") Long id, @RequestParam(required = false) String name,@RequestParam(required = false) String constituency) {
        return areaService.updateArea(id, name,constituency);
    }
}
