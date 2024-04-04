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
        var person = personService.findPersonById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping()
    public ResponseEntity<Person> createPerson(@RequestBody Person personToCreate){
        var personCreated = personService.createPerson(personToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(personCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(personToCreate);
    }

    @PutMapping()
    public ResponseEntity<Person> alterPerson(@RequestBody Person personToUpdate){
        var personUpdated = personService.updatePerson(personToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(personToUpdate.getId())
                .toUri();
        return ResponseEntity.created(location).body(personToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        boolean deleted = personService.deletePersonById(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Retorna resposta 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Retorna resposta 404 Not Found se a pessoa não for encontrada
        }
    }
}
