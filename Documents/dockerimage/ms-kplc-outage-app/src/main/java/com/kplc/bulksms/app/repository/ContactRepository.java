package com.kplc.bulksms.app.repository;

import com.kplc.bulksms.app.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    @Query(value = "SELECT c FROM Contact c WHERE c.msisdn = :msisdn")
    Optional<Contact> findByMsisdn(@Param("msisdn") String msisdn);

    @Query(value = "SELECT c FROM Contact c WHERE lower(c.userName) = :username")
    Optional<Contact> findByUsername(@Param("username") String username);

    @Query(value = "SELECT c FROM Contact c WHERE lower(c.userName) = :username AND c.password = :password")
    Optional<Contact> findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c set  c.token = null, c.password = :password WHERE lower(c.userName) = :username OR lower(c.email) = :username OR lower(c.msisdn) = :username")
    void updateForgotPassword(@Param("password") String password, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c set c.token = :token WHERE lower(c.userName) = :username OR lower(c.email) = :username OR lower(c.msisdn) = :username")
    void updateRegistrationToken(@Param("token") String token, @Param("username") String username);
}
