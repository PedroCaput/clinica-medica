package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByNameAndBirthDateAndGenderAndMotherName(String name, LocalDate birthDate, String gender, String motherName);
}

