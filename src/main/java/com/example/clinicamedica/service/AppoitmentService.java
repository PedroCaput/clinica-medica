package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Appointment;
import com.example.clinicamedica.domain.repository.AppoitmentRepository;
import com.example.clinicamedica.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AppoitmentService extends ServiceImpl<Appointment, AppoitmentRepository> {
    public AppoitmentService(AppoitmentRepository repository){super(repository);}
}
