package com.example.clinicamedica.domain.model.modelMapper;

import com.example.clinicamedica.domain.model.Schedule;
import com.example.clinicamedica.domain.model.dtos.ScheduleDto;
import com.example.clinicamedica.domain.model.enums.AppointmentType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    private final ModelMapper modelMapper;

    public ScheduleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        //configureModelMapper();
    }
/*
    @PostConstruct
    public void init() {
        configureModelMapper();


    }
    private void configureModelMapper() {
        modelMapper.createTypeMap(ScheduleDto.class, Schedule.class)
                .addMapping(ScheduleDto::getScheduleId, Schedule::setScheduleId)
                .addMapping(ScheduleDto::getDoctorId, Schedule::setDoctorId)
                .addMapping(ScheduleDto::getPatientId, Schedule::setPersonId);
    }

 */

        public Schedule convertToEntity(ScheduleDto scheduleDto) {
            Schedule schedule = new Schedule();
            if (scheduleDto.getScheduleId() != null) {
                schedule.setScheduleId(scheduleDto.getScheduleId());
            }
            schedule.setDoctorId(scheduleDto.getDoctorId());
            schedule.setPersonId(scheduleDto.getPatientId());
            schedule.setDate(scheduleDto.getDate());
            schedule.setTime(scheduleDto.getTime());
            schedule.setSpecialty(scheduleDto.getSpecialty());
            AppointmentType appointmentType = parseAppointmentType(scheduleDto.getAppointmentType());
            schedule.setAppointmentType(appointmentType);
            return schedule;
        }

        public ScheduleDto convertToDto(Schedule schedule) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setScheduleId(schedule.getScheduleId());
            scheduleDto.setDoctorId(schedule.getDoctorId());
            scheduleDto.setPatientId(schedule.getPersonId());
            scheduleDto.setDate(schedule.getDate());
            scheduleDto.setTime(schedule.getTime());
            scheduleDto.setSpecialty(schedule.getSpecialty());
            String appointmentType = parseAppointmentTypeToString(schedule.getAppointmentType());
            scheduleDto.setAppointmentType(appointmentType);
            return scheduleDto;
        }

    private String parseAppointmentTypeToString(AppointmentType appointmentType) {
        return appointmentType.name(); // Retorna o nome do enum como String
    }
    private AppointmentType parseAppointmentType(String appointmentTypeString) {
        try {
            return AppointmentType.valueOf(appointmentTypeString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in AppointmentType" + e);
        }
    }
    }


