package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.model.Patient;
import com.example.clinicamedica.domain.model.Person;
import com.example.clinicamedica.domain.model.dto.PatientDto;
import com.example.clinicamedica.domain.repository.DoctorRepository;
import com.example.clinicamedica.domain.repository.PatientRepository;
import com.example.clinicamedica.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final HealthPlanService healthPlanService;

    @Autowired
    public PersonService(PersonRepository personRepository,
                         PatientRepository patientRepository,
                         DoctorRepository doctorRepository,
                         HealthPlanService healthPlanService) {
        this.personRepository = personRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.healthPlanService = healthPlanService;
    }

    public Person findPersonById(Long id){
        return personRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Person createPerson(Person personToCreate){
        if(personToCreate.getId() != null && personRepository.existsById(personToCreate.getId())){
            throw new IllegalArgumentException("This person ID already exists.");
        } else if (personRepository.existsByNameAndBirthDateAndGenderAndMotherName(
                personToCreate.getName(),
                personToCreate.getBirthDate(),
                personToCreate.getGender(),
                personToCreate.getMotherName())) {
            throw new IllegalArgumentException("This person already exists.");
        }
        return personRepository.save(personToCreate);
    }
    public Doctor createDoctor(Doctor doctorToCreate) {
        try {
            Person personCreated = createPerson(doctorToCreate);
            return doctorRepository.save(doctorToCreate); // Assuming Doctor has a constructor that takes a Person object
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error creating doctor: " + e.getMessage());
        }
    }
    public Patient createPatient(Patient patientToCreate) {
        try {
            Person personCreated = createPerson(patientToCreate);
            System.out.println("Pegando o healthPlanName do patientToCreate: " + patientToCreate.getHealthPlan().getHealthPlanName());
            healthPlanService.createHealthPlan(patientToCreate.getHealthPlan());
            return patientRepository.save(patientToCreate); // Assuming Patient has a constructor that takes a Person object
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error creating patient: " + e.getMessage());
        }
    }


}
