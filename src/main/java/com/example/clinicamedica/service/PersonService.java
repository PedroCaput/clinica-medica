package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Person;
import com.example.clinicamedica.domain.repository.PersonRepository;
import com.example.clinicamedica.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends ServiceImpl<Person, PersonRepository> {

    public PersonService(PersonRepository repository) {
        super(repository);
    }
}
