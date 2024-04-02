package com.example.clinicamedica.domain.model;

import com.example.clinicamedica.domain.model.identifiable.Identifiable;
import jakarta.persistence.*;

@Entity(name = "tb_patient")
public class Patient implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    private HealthPlan healthPlan;
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
