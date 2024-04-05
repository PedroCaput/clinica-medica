package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM tb_doctor d WHERE d.id = :id")
    Optional<Doctor> findDoctorByPersonId(@Param("id") Long id);
/*
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_doctor (id, specialty) VALUES (:id, :specialty)", nativeQuery = true)
    void saveDoctorByPersonId(@RequestBody Long id, String specialty);
 */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_doctor (id, specialty) VALUES (:id, :specialty)", nativeQuery = true)
    void saveDoctorByPersonId(@Param("id") Long id, @Param("specialty") String specialty);
}
