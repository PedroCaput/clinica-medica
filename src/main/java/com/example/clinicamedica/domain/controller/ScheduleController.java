package com.example.clinicamedica.domain.controller;

import com.example.clinicamedica.domain.model.Schedule;
import com.example.clinicamedica.domain.model.dtos.ScheduleDto;
import com.example.clinicamedica.domain.model.modelMapper.ScheduleMapper;
import com.example.clinicamedica.service.ScheduleService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.modelmapper.ModelMapper;

import java.net.URI;
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ModelMapper modelMapper;

    private final ScheduleMapper scheduleMapper;

    public ScheduleController(ScheduleService scheduleService,
                              ModelMapper modelMapper,
                              ScheduleMapper scheduleMapper){
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
        this.scheduleMapper = scheduleMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findById(@PathVariable Long id){
        var schedule = scheduleService.findScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping()
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDtoToCreate){
        var scheduleCreated = scheduleService.createSchedule(scheduleDtoToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(scheduleCreated.getScheduleId())
                .toUri();
        return ResponseEntity.created(location).body(scheduleDtoToCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable("id") Long scheduleId, @RequestBody ScheduleDto scheduleDto) {
        Schedule scheduleToUpdate = scheduleMapper.convertToEntity(scheduleDto);
        scheduleToUpdate.setScheduleId(scheduleId);

        Schedule updatedSchedule = scheduleService.updateSchedule(
                scheduleToUpdate.getScheduleId(),
                scheduleToUpdate.getDoctorId(),
                scheduleToUpdate.getPersonId(),
                scheduleToUpdate.getSpecialty(),
                scheduleToUpdate.getDate(),
                scheduleToUpdate.getTime(),
                scheduleToUpdate.getAppointmentType());
        return ResponseEntity.ok().body(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
        boolean deleted = scheduleService.deleteScheduleById(id);
        if(deleted) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
