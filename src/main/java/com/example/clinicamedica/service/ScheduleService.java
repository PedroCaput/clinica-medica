package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Schedule;
import com.example.clinicamedica.domain.repository.ScheduleRepository;
import com.example.clinicamedica.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService extends ServiceImpl<Schedule, ScheduleRepository> {
    public ScheduleService(ScheduleRepository repository){super(repository);}
}
