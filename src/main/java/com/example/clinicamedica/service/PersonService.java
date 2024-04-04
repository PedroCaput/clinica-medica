package com.example.clinicamedica.service;


import com.example.clinicamedica.domain.model.Person;
import com.example.clinicamedica.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public Person findPersonById(Long id){
        return personRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Person createPerson(Person personToCreate){
        if(personToCreate.getId() != null && personRepository.existsById(personToCreate.getId())){
            throw new IllegalArgumentException("This person ID already exists.");
        }
        else if (personRepository.existsPerson(
                personToCreate.getName(),
                personToCreate.getBirthDate(),
                personToCreate.getGender(),
                personToCreate.getMotherName()))
        {
            throw new IllegalArgumentException("This person already exists.");
        }
        else {
            return personRepository.save(personToCreate);
        }
    }

    public Person updatePerson(Person personToUpdate){
        if(personToUpdate.getId() == null || !personRepository.existsById(personToUpdate.getId()))
        {
            throw new IllegalArgumentException("This person does not exists.");
        }
        return personRepository.save(personToUpdate);
    }

    public boolean deletePersonById(Long id){
        if(id == null || !personRepository.existsById(id))
        {
            throw new IllegalArgumentException("This person does not exists.");
        }
        personRepository.deleteById(id);
        return true;
    }
}
