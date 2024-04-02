package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.repository.DoctorRepository;
import com.example.clinicamedica.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DoctorService extends ServiceImpl<Doctor, DoctorRepository> {

    public DoctorService(DoctorRepository repository) {
        super(repository);
    }
}
