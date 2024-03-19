package com.kplc.bulksms.app.service;

import com.kplc.bulksms.app.excepions.CustomErrorException;
import com.kplc.bulksms.app.excepions.CustomExtraErrorException;
import com.kplc.bulksms.app.model.Constituency;
import com.kplc.bulksms.app.model.dto.AreaDto;
import com.kplc.bulksms.app.model.dto.ConstituencyDto;
import com.kplc.bulksms.app.repository.AreaRepository;
import com.kplc.bulksms.app.repository.ConstituencyRepository;
import com.kplc.bulksms.app.repository.CountyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ConstituencyService {

    private final ConstituencyRepository constituencyRepository;
    private final AreaRepository areaRepository;

    public ConstituencyService(CountyRepository countyRepository, ConstituencyRepository constituencyRepository, AreaRepository areaRepository) {
        this.constituencyRepository = constituencyRepository;
        this.areaRepository = areaRepository;
    }

    public ResponseEntity<Object> getConstituency(Long id) {
        Optional<Constituency> constituency = constituencyRepository.findById(id);
        if (constituency.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Constituency with id: " + id);
        }
        ConstituencyDto cDto = constituency.map(dto -> new ConstituencyDto(dto.getCstId(), dto.getName())).orElseThrow(null);
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", cDto);
    }

    public ResponseEntity<Object> getConstituencies() {
        List<ConstituencyDto> constituencyDtos = constituencyRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(dto -> new ConstituencyDto(dto.getCstId(), dto.getName()))
                .toList();

        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", constituencyDtos);

    }

    public ResponseEntity<Object> createNewConstituency(Constituency constituency) {

        Optional<Constituency> optionalConstituency = constituencyRepository.findByName(constituency.getName());
        if (optionalConstituency.isPresent()) {
            throw new CustomErrorException(HttpStatus.FOUND, "Constituency already exists");
        }
        if (null != constituency.getName() && !constituency.getName().isEmpty()) {
            constituencyRepository.save(constituency);
            throw new CustomErrorException(HttpStatus.CREATED, "Constituency created successfully");
        } else {
            throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong");
        }
    }

    public ResponseEntity<Object> deleteConstituency(Long id) {
        boolean exists = constituencyRepository.existsById(id);
        if (!exists) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Constituency with id: " + id);
        }
        constituencyRepository.deleteById(id);
        areaRepository.deleteAllByConstituencyId(id);
        throw new CustomErrorException(HttpStatus.OK, "Constituency deleted Successfully");
    }

    @Transactional
    public ResponseEntity<Object> updateConstituency(Long cstId, String name) {
        Constituency constituency = constituencyRepository.findById(cstId).orElseThrow(() ->
                new IllegalArgumentException("No such constituency with id: " + cstId));
        if (name != null && !name.isEmpty() && !Objects.equals(constituency.getName(), name)) {
            constituency.setName(name);
            constituencyRepository.save(constituency);
            throw new CustomErrorException(HttpStatus.CREATED, "Constituency updated successfully");
        }
        throw new CustomErrorException(HttpStatus.CREATED, "No update done");
    }


    public ResponseEntity<Object> getConstituencyAreas(Long id) {
        Constituency constituency = constituencyRepository.findById(id).orElseThrow(null);
        if (constituency == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such constituency with id: " + id);
        }
        List<AreaDto> areaDtos = constituency
                .getAreas()
                .stream()
                .filter(Objects::nonNull)
                .map(dto ->new AreaDto(dto.getAreaId(), dto.getName()))
                .toList();

        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", areaDtos);

    }

}
    

