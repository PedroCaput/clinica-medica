package com.example.clinicamedica.domain.model;

import com.example.clinicamedica.domain.model.enums.AppointmentType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "tb_schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Long doctorId;
    private Long personId;

    private LocalDate date;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;
    private String specialty;

    public Schedule(){}
    public Schedule(Long scheduleId,
                    Long doctorId, Long personId,
                    LocalDate date, LocalTime time,
                    AppointmentType appointmentType,
                    String specialty){
        this.scheduleId = scheduleId;
        this.doctorId = doctorId;
        this.personId = personId;
        this.date = date;
        this.time = time;
        this.appointmentType = appointmentType;
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

   public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId;}

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) { this.time = time; }

    public AppointmentType getAppointmentType(){ return appointmentType; }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }
}
