package com.example.clinicamedica.domain.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDto {
    @Schema(example = "1", description = "Schedule ID. Not necessary to create")
    private Long scheduleId;

    @Schema(example = "1", description = "Doctor ID")
    private Long doctorId;
    @Schema(example = "2", description = "Patient ID")
    private Long patientId;

    @Schema(example = "ortopedista", description = "Doctor speciality")
    private String specialty;

    @Schema(example = "2024-04-11")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Schema(example = "10:00")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    @Schema(example = "APPOINTMENT", description = "APPOINTMENT or SURGERY")
    private String appointmentType;

    public ScheduleDto(){}

    public ScheduleDto(Long doctorId, Long patientId,
                       LocalDate date, LocalTime time,
                       String appointmentType,
                       String specialty){
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.appointmentType = appointmentType;
        this.specialty = specialty;
    }
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
}
