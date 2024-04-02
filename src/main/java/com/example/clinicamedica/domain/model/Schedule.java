package com.example.clinicamedica.domain.model;

import com.example.clinicamedica.domain.model.identifiable.Identifiable;
import jakarta.persistence.*;

@Entity(name = "tb_schedule")
public class Schedule implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Doctor doctor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patient patient;

    private String time;

    @OneToOne(cascade = CascadeType.ALL)
    private Appointment appointmentType;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
