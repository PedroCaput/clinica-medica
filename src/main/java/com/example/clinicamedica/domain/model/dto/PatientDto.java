package com.example.clinicamedica.domain.model.dto;

import java.time.LocalDate;

public class PatientDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String healthPlanName;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHealthPlanName() {
        return healthPlanName;
    }

    public void setHealthPlanName(String healthPlanName) {
        this.healthPlanName = healthPlanName;
    }
}
