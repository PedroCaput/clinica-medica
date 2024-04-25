package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM tb_doctor d WHERE d.id = :id")
    Optional<Doctor> findDoctorByPersonId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_doctor (person_id, specialty) VALUES (:personId, :specialty)", nativeQuery = true)
    void saveDoctorByPersonId(@Param("personId") Long personId, @Param("specialty") String specialty);

    @Query("SELECT d FROM tb_doctor d WHERE d.id = :doctorId")
    Doctor findByIdForMap(@Param("doctorId") Long doctorId);
}
