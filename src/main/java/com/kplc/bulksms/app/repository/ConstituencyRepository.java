package com.kplc.bulksms.app.repository;

import com.kplc.bulksms.app.model.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConstituencyRepository extends JpaRepository<Constituency,Long> {
    @Query(value = "SELECT c FROM Constituency c WHERE c.name = :name")
    Optional<Constituency> findByName(@Param("name") String name);

    @Query(value = "DELETE FROM Constituency c WHERE c.ctyId = :ctyId")
    void  deleteAllByCountyId(@Param("ctyId") Long ctyId);
}
