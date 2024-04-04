package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Schedule;
import com.example.clinicamedica.domain.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule findScheduleById(Long id){
        return scheduleRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Schedule createSchedule(Schedule scheduleToCreate){
        if(scheduleRepository.existsById(scheduleToCreate.getId())){
            throw new IllegalArgumentException("This schedule already exists.");
        }
        // TODO: fazer a checagem da data
        // TODO: fazer a checagem do horário para o tipo do agendamento (8h as 11h consultas / 13h as 17h cirurgias)
        // TODO: fazer a checagem da agenda do médico
        // TODO: fazer a checagem da agenda do paciente
        return scheduleRepository.save(scheduleToCreate);
    }
}
