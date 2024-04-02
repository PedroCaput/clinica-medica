package com.example.clinicamedica.domain.model;

import com.example.clinicamedica.domain.model.identifiable.Identifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_health_plan")
public class HealthPlan implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String healthPlanName;
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHealthPlanName() {
        return healthPlanName;
    }

    public void setHealthPlanName(String healthPlanName) {
        this.healthPlanName = healthPlanName;
    }
}
