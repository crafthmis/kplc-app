package com.kplc.bulksms.app.repository;

import com.kplc.bulksms.app.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Long> {
    @Query(value = "SELECT c FROM Region c WHERE c.name = :name")
    Optional<Region> findByName(@Param("name") String name);
}
