package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Patient;
import com.example.clinicamedica.domain.repository.PatientRepository;
import com.example.clinicamedica.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends ServiceImpl<Patient, PatientRepository> {

    public PatientService(PatientRepository repository) {
        super(repository);
    }
}
