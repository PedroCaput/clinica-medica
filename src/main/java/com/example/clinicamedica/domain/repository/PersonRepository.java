package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM tb_person p WHERE p.name = :name AND p.birthDate = :birthDate AND p.gender = :gender AND p.motherName = :motherName")
    boolean existsPerson(String name, LocalDate birthDate, String gender, String motherName);

    @Query("SELECT p FROM tb_person p WHERE p.id = :personId")
    Person findPersonByIdForMap(@Param("personId") Long personId);
}
