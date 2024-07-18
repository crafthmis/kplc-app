package com.kplc.bulksms.app.service;

import com.kplc.bulksms.app.excepions.CustomErrorException;
import com.kplc.bulksms.app.excepions.CustomExtraErrorException;
import com.kplc.bulksms.app.model.County;
import com.kplc.bulksms.app.model.dto.ConstituencyDto;
import com.kplc.bulksms.app.model.dto.CountyDto;
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
public class CountyService {

    private final CountyRepository countyRepository;
    private final ConstituencyRepository constituencyRepository;

    public CountyService(CountyRepository countyRepository, AreaRepository areaRepository, ConstituencyRepository constituencyRepository) {
        this.countyRepository = countyRepository;
        this.constituencyRepository = constituencyRepository;
    }

    public ResponseEntity<Object> getCounty(Long id) {
        Optional<County> county = countyRepository.findById(id);
        if (county.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such County with id: " + id);
        }
        CountyDto cDto = county.map(cty -> new CountyDto(cty.getCntId(), cty.getCode(), cty.getName())).orElseThrow(null);
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", cDto);
    }

    public ResponseEntity<Object> getCounties() {
        List<CountyDto> countyDtos = countyRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(cty -> new CountyDto(cty.getCntId(), cty.getCode(), cty.getName()))
                .toList();

        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", countyDtos);

    }

    public ResponseEntity<Object> createNewCounty(County cty) {

        Optional<County> optionalCounty = countyRepository.findByName(cty.getName());
        if (optionalCounty.isPresent()) {
            throw new CustomErrorException(HttpStatus.FOUND, "County already exists");
        }
        if (null != cty.getName() && !cty.getName().isEmpty() && null != cty.getCode() && !cty.getCode().isEmpty()) {
            countyRepository.save(cty);
            throw new CustomErrorException(HttpStatus.CREATED, "County created successfully");
        } else {
            throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong");
        }
    }

    public ResponseEntity<Object> deleteCounty(Long id) {
        boolean exists = countyRepository.existsById(id);
        if (!exists) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such County with id: " + id);
        }
        countyRepository.deleteById(id);
        constituencyRepository.deleteAllByCountyId(id);
        throw new CustomErrorException(HttpStatus.OK, "County deleted Successfully");
    }

    @Transactional
    public ResponseEntity<Object> updateCounty(Long ctyId, String name) {
        County cty = countyRepository.findById(ctyId).orElseThrow(() ->
                new IllegalArgumentException("No such county with id: " + ctyId));
        if (name != null && !name.isEmpty() && !Objects.equals(cty.getName(), name)) {
            cty.setName(name);
            countyRepository.save(cty);
            throw new CustomErrorException(HttpStatus.CREATED, "County updated successfully");
        }
        throw new CustomErrorException(HttpStatus.CREATED, "No update done");
    }


    public ResponseEntity<Object> getCountyConstituencies(Long id) {
        County county = countyRepository.findById(id).orElseThrow(null);
        if (county == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such county with id: " + id);
        }
        List<ConstituencyDto> constituencyDtos = county
                .getConstituencies()
                .stream()
                .filter(Objects::nonNull)
                .map(dto ->
                        new ConstituencyDto(dto.getCstId(), dto.getName()))
                .toList();

        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", constituencyDtos);

    }

}
    

