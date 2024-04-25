package com.example.clinicamedica.service;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.model.Person;
import com.example.clinicamedica.domain.model.Schedule;
import com.example.clinicamedica.domain.model.dtos.ScheduleDto;
import com.example.clinicamedica.domain.model.enums.AppointmentType;
import com.example.clinicamedica.domain.model.modelMapper.ScheduleMapper;
import com.example.clinicamedica.domain.repository.DoctorRepository;
import com.example.clinicamedica.domain.repository.PersonRepository;
import com.example.clinicamedica.domain.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleService {
    private final DoctorRepository doctorRepository;
    private final PersonRepository personRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    private ScheduleMapper scheduleMapper;

    public ScheduleService(DoctorRepository doctorRepository,
                           PersonRepository personRepository,
                           ScheduleRepository scheduleRepository,
                           ModelMapper modelMapper,
                           ScheduleMapper scheduleMapper
    ) {
        this.doctorRepository = doctorRepository;
        this.personRepository = personRepository;
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
        this.scheduleMapper = scheduleMapper;
    }

    public Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Doctor findDoctorByPersonId(Long id) {
        return doctorRepository.findDoctorByPersonId(id).orElseThrow(NoSuchElementException::new);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Schedule createSchedule(ScheduleDto scheduleDto) {
        Long doctorId = scheduleDto.getDoctorId();
        Long patientId = scheduleDto.getPatientId();
        LocalDate date = scheduleDto.getDate();
        LocalTime time = scheduleDto.getTime();
        String specialty = scheduleDto.getSpecialty();
        AppointmentType appointmentType = AppointmentType.valueOf(scheduleDto.getAppointmentType());

        if (doctorId != null && !doctorRepository.existsById(doctorId))
        {
            throw new IllegalArgumentException("This doctor does not exists.");
        }
        else if (patientId != null && !personRepository.existsById(patientId))
        {
            throw new IllegalArgumentException("This patient does not exists.");
        }
        else if (Objects.equals(doctorId, patientId))
        {
            throw new IllegalArgumentException("Doctor cannot schedule appointment with yourself");
        }
        else if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())))
        {
            throw new IllegalArgumentException("You cannot schedule appointments for past.");
        }
        else if (scheduleRepository
                .scheduleIsOccupiedByDoctorId(
                        scheduleDto.getDoctorId(),
                        scheduleDto.getDate(),
                        scheduleDto.getTime()))
        {
            throw new IllegalArgumentException("Doctor schedule is occupied in this time.");
        }
        else if (scheduleRepository
                .scheduleIsOccupiedByPersonId(
                        scheduleDto.getPatientId(),
                        scheduleDto.getDate(),
                        scheduleDto.getTime()))
        {
            throw new IllegalArgumentException("Person schedule is occupied in this time.");
        }
        else if (!Objects.equals(scheduleDto.getAppointmentType(), "APPOINTMENT") &&
                !Objects.equals(scheduleDto.getAppointmentType(), "SURGERY"))
        {
            throw new IllegalArgumentException("Appointment type must be appointment or surgery.");
        }
        else if (Objects.equals(scheduleDto.getAppointmentType(), "APPOINTMENT")
                && (time.isBefore(LocalTime.of(8, 0))
                    || time.isAfter(LocalTime.of(11, 0))))
        {
            throw new IllegalArgumentException("This appointment cannot be schedule in this time.");
        }
        else if (Objects.equals(scheduleDto.getAppointmentType(), "SURGERY")
            && (time.isBefore(LocalTime.of(13, 0))
                    || time.isAfter(LocalTime.of(17, 0))))
        {
            throw new IllegalArgumentException("This appointment cannot be schedule in this time.");
        }
        else
        {
            try
            {
                Schedule scheduleToSave = scheduleMapper.convertToEntity(scheduleDto);
                return scheduleRepository.save(scheduleToSave);
            }
            catch (Exception exception)
            {
                throw new IllegalArgumentException("Error creating schedule: " + exception.getMessage());
            }
        }
    }

    public Schedule updateSchedule(Long scheduleId, Long doctorId, Long personId, String specialty, LocalDate date, LocalTime time, AppointmentType appointmentType) {
        try {
            System.out.println("Tentou");
            if(scheduleRepository.existsById(scheduleId)){
                Schedule scheduleToUpdate = scheduleRepository.findScheduleById(scheduleId);

                scheduleToUpdate.setScheduleId(scheduleId);
                scheduleToUpdate.setDoctorId(doctorId);
                scheduleToUpdate.setPersonId(personId);
                scheduleToUpdate.setSpecialty(specialty);
                scheduleToUpdate.setDate(date);
                scheduleToUpdate.setTime(time);
                scheduleToUpdate.setAppointmentType(appointmentType);
                System.out.println("--------->" + scheduleToUpdate);
                scheduleRepository.save(scheduleToUpdate);
                return scheduleToUpdate;
            }
            else {
                throw new IllegalArgumentException("This schedule does not exists.");
            }
        }
        catch (Exception exception)
        {
            throw new IllegalArgumentException("Error updating schedule" + exception);
        }
    }

    public boolean deleteScheduleById(Long id) {
        if (id == null || !scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("This schedule does not exists.");
        }
        scheduleRepository.deleteById(id);
        return true;
    }

    public List<ScheduleDto> findNextAvailableSlotsForDoctor(Long doctorId) {
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysFromNow = today.plusDays(5);

        List<Schedule> schedules = scheduleRepository.findFreeScheduleByDoctorIdFromDateToDate(
                doctorId,
                today,
                fiveDaysFromNow
        );

        List<ScheduleDto> availableSlots = new ArrayList<>();
        for (Schedule schedule : schedules) {

            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setDoctorId(schedule.getDoctorId());
            scheduleDto.setAppointmentType(schedule.getAppointmentType().toString());
            scheduleDto.setDate(schedule.getDate());
            scheduleDto.setTime(schedule.getTime());

            availableSlots.add(scheduleDto);
        }

        return availableSlots;
    }
}
