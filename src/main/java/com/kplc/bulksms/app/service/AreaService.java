package com.kplc.bulksms.app.service;

import com.kplc.bulksms.app.excepions.CustomErrorException;
import com.kplc.bulksms.app.excepions.CustomExtraErrorException;
import com.kplc.bulksms.app.model.Area;
import com.kplc.bulksms.app.model.dto.AreaDto;
import com.kplc.bulksms.app.model.dto.ContactDto;
import com.kplc.bulksms.app.repository.AreaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public ResponseEntity<Object> getAreas() {
        List<AreaDto> areaDtos = areaRepository.findAll(Sort.by(Sort.Direction.ASC, "areaId")).stream()
                .filter(Objects::nonNull)
                .map(area -> new AreaDto(area.getAreaId(),area.getName()))
                .toList();
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", areaDtos);
    }
    public ResponseEntity<Object> getArea(Long id) {
        Optional<Area> area = areaRepository.findById(id);
        if (area.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Area with id: " + id);
        }
        AreaDto areaDto = area.map(dto -> new AreaDto(dto.getAreaId(), dto.getName())).orElseThrow(null);
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", areaDto);
    }

    public ResponseEntity<Object> createNewArea(Area area) {

        Optional<Area> optionalArea = areaRepository.findByName(area.getName());
        if (optionalArea.isPresent()) {
            throw new CustomErrorException(HttpStatus.FOUND, "Area already exists");
        }
        if (null != area.getName() && !area.getName().isEmpty()) {
            areaRepository.save(area);
            throw new CustomErrorException(HttpStatus.CREATED, "Area created successfully");
        } else {
            throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong");
        }
    }

    public ResponseEntity<Object> deleteArea(Long id) {
        boolean exists = areaRepository.existsById(id);
        if (!exists) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Area with id: " + id);
        }
        areaRepository.deleteById(id);
        throw new CustomErrorException(HttpStatus.OK, "Area deleted Successfully");
    }

    @Transactional
    public ResponseEntity<Object> updateArea(Long ctyId,String name,String constituency) {
        Area area = areaRepository.findById(ctyId).orElseThrow(() ->
                new IllegalArgumentException("No such area with id: " + ctyId));
        if (name != null && !name.isEmpty() && !Objects.equals(area.getName(), name)) {
            area.setName(name);
            areaRepository.save(area);
            throw new CustomErrorException(HttpStatus.CREATED, "Area updated successfully");
        }
        throw new CustomErrorException(HttpStatus.CREATED, "No update done");
    }

    public ResponseEntity<Object> getAreaContacts(Long id) {
        Area area = areaRepository.findById(id).orElseThrow(null);
        if (area == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Area with id: " + id);
        }
        List<ContactDto> contactDtos = area
                .getContacts()
                .stream()
                .filter(Objects::nonNull)
                .map(dto ->new ContactDto(dto.getCntId(), dto.getArea().getName(), dto.getMsisdn()))
                .toList();

        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", contactDtos);

    }

}
    

