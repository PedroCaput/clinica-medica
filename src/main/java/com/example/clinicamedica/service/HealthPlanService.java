package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.HealthPlan;
import com.example.clinicamedica.domain.repository.HealthPlanRepository;
import com.example.clinicamedica.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HealthPlanService extends ServiceImpl<HealthPlan, HealthPlanRepository> {
    public HealthPlanService(HealthPlanRepository repository){super(repository);}
}


