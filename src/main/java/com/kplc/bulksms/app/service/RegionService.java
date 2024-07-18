package com.kplc.bulksms.app.service;

import com.kplc.bulksms.app.excepions.CustomErrorException;
import com.kplc.bulksms.app.excepions.CustomExtraErrorException;
import com.kplc.bulksms.app.model.County;
import com.kplc.bulksms.app.model.Region;
import com.kplc.bulksms.app.model.dto.CountyDto;
import com.kplc.bulksms.app.model.dto.RegionDto;
import com.kplc.bulksms.app.repository.AreaRepository;
import com.kplc.bulksms.app.repository.CountyRepository;
import com.kplc.bulksms.app.repository.RegionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class RegionService {
    private final RegionRepository regionRepository;
    private final CountyRepository countyRepository;

    public RegionService(RegionRepository regionRepository, AreaRepository areaRepository, CountyRepository countyRepository) {
        this.regionRepository = regionRepository;
        this.countyRepository = countyRepository;
    }

    public List<Region> getRegions() {
        List<RegionDto> regionDtos =  regionRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(reg -> new RegionDto(reg.getRegId(), reg.getName()))
                .toList();

        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved",regionDtos);
    }

    public ResponseEntity<Object> getRegion(Long id) {
        Optional<Region> region = regionRepository.findById(id);
        if (region.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such County with id: " + id);
        }
        RegionDto regionDto = region.map(dto -> new RegionDto(dto.getRegId(), dto.getName())).orElseThrow(null);
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", regionDto);
    }

    public ResponseEntity<Object> createNewRegion(Region region) {

        Optional<Region> optionalRegion = regionRepository.findByName(region.getName());
        if (optionalRegion.isPresent()) {
            throw new CustomErrorException(HttpStatus.FOUND, "Region already exists");
        }
        if (null != region.getName() && !region.getName().isEmpty()) {
            regionRepository.save(region);
            throw new CustomErrorException(HttpStatus.CREATED, "Region created successfully");
        } else {
            throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong");
        }
    }

    public ResponseEntity<Object> deleteRegion(Long id) {
        boolean exists = regionRepository.existsById(id);
        if (!exists) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + id);
        }
        regionRepository.deleteById(id);
        countyRepository.deleteAllByRegionId(id);
        throw new CustomErrorException(HttpStatus.OK, "Region deleted Successfully");
    }

    @Transactional
    public ResponseEntity<Object> updateRegion(Long RegionId, String name) {
        Region region = regionRepository.findById(RegionId).orElseThrow(() ->
                new IllegalArgumentException("No such region with id: " + RegionId));
        if (name != null && !name.isEmpty() && !Objects.equals(region.getName(), name)) {
            region.setName(name);
            regionRepository.save(region);
            throw new CustomErrorException(HttpStatus.CREATED, "Region updated successfully");
        }

        throw new CustomErrorException(HttpStatus.CREATED, "No update done");
    }

        public ResponseEntity<Object> getRegionCounties(Long id) {
            Region region = regionRepository.findById(id).orElseThrow(null);
            if (region==null) {
                throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such region with id: " + id);
            }
            List<CountyDto> countyDtos = region
                                        .getCounties()
                                        .stream()
                                        .filter(Objects::nonNull)
                                        .map(cty ->
                                        new CountyDto(cty.getCntId(),cty.getCode(),cty.getName()))
                                         .toList();

            throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved",countyDtos);

        }
}

