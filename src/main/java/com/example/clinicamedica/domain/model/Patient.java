package com.example.clinicamedica.domain.model;

import jakarta.persistence.*;

@Entity(name = "tb_patient")
public class Patient extends Person {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    private HealthPlan healthPlan;
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public HealthPlan getHealthPlan(){return healthPlan;}

    public void setHealthPlan(HealthPlan healthPlan) {this.healthPlan = healthPlan;}
}
