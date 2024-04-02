package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM tb_person p WHERE p.name = :name AND p.age = :age AND p.gender = :gender AND p.motherName = :motherName")
    boolean existsPerson(String name, int age, String gender, String motherName);
}
