package com.example.clinicamedica.domain.model;

import jakarta.persistence.*;

@Entity(name = "tb_doctor")
public class Doctor extends Person {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;
    private String specialty;

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
