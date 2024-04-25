package com.example.clinicamedica.domain.controller;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findDoctorByPersonId(@PathVariable Long id){
        var doctor = doctorService.findDoctorByPersonId(id);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping()
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctorToCreate){
        var doctorCreated = doctorService.createDoctor(doctorToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(doctorCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(doctorCreated);
    }

    @PutMapping()
    public ResponseEntity<Doctor> alterDoctor(@RequestBody Doctor doctorToUpdate){
        var doctorUpdated = doctorService.updateDoctor(doctorToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(doctorToUpdate.getId())
                .toUri();
        return ResponseEntity.created(location).body(doctorToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        boolean deleted = doctorService.deleteDoctorById(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Retorna resposta 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Retorna resposta 404 Not Found se a pessoa não for encontrada
        }
    }
}
