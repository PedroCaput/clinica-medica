package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.HealthPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthPlanRepository extends JpaRepository<HealthPlan, Long> {
    boolean existsByHealthPlanName(String healthPlanName);
}
