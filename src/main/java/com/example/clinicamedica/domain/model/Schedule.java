package com.example.clinicamedica.domain.model;

import jakarta.persistence.*;

@Entity(name = "tb_schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Doctor doctor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;

    private String time;

    @OneToOne(cascade = CascadeType.ALL)
    private Appointment appointmentType;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
