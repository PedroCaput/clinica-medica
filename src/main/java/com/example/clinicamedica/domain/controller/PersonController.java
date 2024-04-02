package com.example.clinicamedica.domain.controller;

import com.example.clinicamedica.domain.model.Person;
import com.example.clinicamedica.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id){
        var person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping()
    public ResponseEntity<Person> create(@RequestBody Person personToCreate){
        var personCreated = personService.create(personToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(personCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(personToCreate);
    }
}
