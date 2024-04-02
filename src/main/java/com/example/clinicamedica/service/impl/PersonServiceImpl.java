package com.example.clinicamedica.service.impl;

import com.example.clinicamedica.domain.model.identifiable.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public class PersonServiceImpl <Entity extends Identifiable, Repository extends JpaRepository<Entity, Long>> {
    private final Repository repository;

    public PersonServiceImpl(Repository repository) {
        this.repository = repository;
    }

    public Entity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public Entity createPerson(Entity entityToCreate) {
        if (entityToCreate.getId() != null && repository.existsById(entityToCreate.getId())) {
            throw new IllegalArgumentException("This " + entityToCreate.getClass().getSimpleName() + " ID already exists.");
        }
        return repository.save(entityToCreate);
    }
}

