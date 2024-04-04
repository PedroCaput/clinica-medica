package com.example.clinicamedica.service;


import com.example.clinicamedica.domain.controller.exception.GlobalExceptionHandler;
import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.repository.DoctorRepository;
import com.example.clinicamedica.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    private final PersonRepository personRepository;

    public DoctorService(DoctorRepository doctorRepository,
                         PersonRepository personRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.personRepository = personRepository;
    }

    public Doctor findDoctorById(Long id){
        return doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Doctor createDoctor(Long id,Doctor doctorToCreate){
        if(id != null && doctorRepository.existsById(id)){
            throw new IllegalArgumentException("This doctor ID already exists.");
        }
        else {
            try{
                if(personRepository.existsById(doctorToCreate.getId())){
                    return doctorRepository.save(doctorToCreate);
                }
                throw new IllegalArgumentException("The ID must be the same of person ID.");
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("Error creating doctor: " + exception.getMessage());
            }
        }
    }

    public Doctor updateDoctor(Long id, Doctor doctorToUpdate){
        if(id == null || !doctorRepository.existsById(doctorToUpdate.getId()))
        {
            throw new IllegalArgumentException("This doctor does not exists.");
        }
        return doctorRepository.save(doctorToUpdate);
    }

    public boolean deleteDoctorById(Long id){
        if(id == null || !doctorRepository.existsById(id))
        {
            throw new IllegalArgumentException("This doctor does not exists.");
        }
        doctorRepository.deleteById(id);
        return true;
    }
}