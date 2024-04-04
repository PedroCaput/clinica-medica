package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.HealthPlan;
import com.example.clinicamedica.domain.repository.HealthPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HealthPlanService {
    private final HealthPlanRepository healthPlanRepository;
    @Autowired
    public HealthPlanService(HealthPlanRepository healthPlanRepository){
        this.healthPlanRepository = healthPlanRepository;
    }

    public HealthPlan findHealthPlanById(Long id){
        return healthPlanRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public HealthPlan createHealthPlan(HealthPlan healthPlanToCreate){
        if(healthPlanToCreate.getId() != null && healthPlanRepository.existsById(healthPlanToCreate.getId())){
            throw new IllegalArgumentException("This health plan ID already exists.");
        }
        else if(healthPlanToCreate.getHealthPlanName() != null && healthPlanRepository.existsByHealthPlanName(healthPlanToCreate.getHealthPlanName())){
            throw new IllegalArgumentException("This health plan name already exists.");
        }
        return healthPlanRepository.save(healthPlanToCreate);
    }
}


