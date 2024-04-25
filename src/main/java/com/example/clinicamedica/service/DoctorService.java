package com.example.clinicamedica.service;


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

    public Doctor findDoctorByPersonId(Long id){
        return doctorRepository.findDoctorByPersonId(id).orElseThrow(NoSuchElementException::new);
    }

    public Doctor createDoctor(Doctor doctorToCreate){
        Long id = doctorToCreate.getId();
        String specialty = doctorToCreate.getSpecialty();
        if(id != null && doctorRepository.existsById(id)){
            throw new IllegalArgumentException("This doctor ID already exists.");
        }
        else {
            try{
                // Preciso checar se existe uma pessoa com esse ID
                if(personRepository.existsById(id)){
                    doctorRepository.saveDoctorByPersonId(id, specialty);
                    return doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
                }
                throw new IllegalArgumentException("The ID must be the same of person ID.");
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("Error creating doctor: " + exception.getMessage());
            }
        }
    }

    public Doctor updateDoctor(Doctor doctorToUpdate){
        if(doctorToUpdate.getId() == null || !doctorRepository.existsById(doctorToUpdate.getId()))
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