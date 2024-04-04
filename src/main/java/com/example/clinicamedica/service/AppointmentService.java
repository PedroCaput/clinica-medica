package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Appointment;
import com.example.clinicamedica.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment findAppointmentById(Long id){
        return appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Appointment createAppointment(Appointment appointmentToCreate){
        if(appointmentRepository.existsById(appointmentToCreate.getId())){
            throw new IllegalArgumentException("This appointment ID already exists.");
        }
        return appointmentRepository.save(appointmentToCreate);
    }
}
