package com.example.clinicamedica.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity(name = "tb_doctor")
@JsonIgnoreProperties({"person"})
public class Doctor {
    @Id
    @JoinColumn(name = "person_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    private String specialty;

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
