package com.kplc.bulksms.app.repository;

import com.kplc.bulksms.app.model.Area;
import com.kplc.bulksms.app.model.dto.AreaDto;
import com.kplc.bulksms.app.model.dto.ConstituencyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area,Long> {
    @Query(value = "SELECT c FROM Area c WHERE c.name = :name")
    Optional<Area> findByName(@Param("name") String name);

    @Query(value = "DELETE FROM Area c WHERE c.cstId = :cstId")
    void  deleteAllByConstituencyId(@Param("cstId") Long cstId);
}
