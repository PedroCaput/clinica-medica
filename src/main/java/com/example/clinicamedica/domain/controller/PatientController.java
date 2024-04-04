package com.example.clinicamedica.domain.controller;

import com.example.clinicamedica.domain.model.Patient;
import com.example.clinicamedica.domain.model.Person;
import com.example.clinicamedica.domain.model.dto.PatientDto;
import com.example.clinicamedica.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PersonService personService;
    private final ModelMapper modelMapper;

    public PatientController(PersonService personService, ModelMapper modelMapper){
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> findById(@PathVariable Long id){
        Person patient = personService.findPersonById(id);
        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        return ResponseEntity.ok(patientDto);
    }

    @PostMapping
    public ResponseEntity<PatientDto> create(@RequestBody Patient request) {
        Patient patientToCreate = personService.createPatient(request);
        PatientDto patientDto = modelMapper.map(patientToCreate, PatientDto.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(patientDto);
    }
}
