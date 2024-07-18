package com.kplc.bulksms.app.repository;

import com.kplc.bulksms.app.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CountyRepository extends JpaRepository<County,Long> {
    @Query(value = "SELECT c FROM County c WHERE c.name = :name")
    Optional<County> findByName(@Param("name") String name);

    @Query(value = "DELETE FROM County c WHERE c.regId = :regionId")
    void  deleteAllByRegionId(@Param("regionId") Long regionId);
}
