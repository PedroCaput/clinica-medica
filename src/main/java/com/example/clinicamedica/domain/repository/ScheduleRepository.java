package com.example.clinicamedica.domain.repository;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.model.Schedule;
import com.example.clinicamedica.domain.model.enums.AppointmentType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM tb_schedule s WHERE s.scheduleId = :id")
    Schedule findScheduleById(@Param("id") Long id);
    @Query("SELECT s FROM tb_schedule s " +
            "WHERE s.doctorId = :doctorId " +
            "AND s.date BETWEEN :fromDate AND :toDate " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM tb_schedule s2 " +
            "    WHERE s2.doctorId = s.doctorId " +
            "    AND s2.date = s.date " +
            "    AND s2.time = s.time " +
            ")")
    List<Schedule> findFreeScheduleByDoctorIdFromDateToDate(@Param("doctorId") Long doctorId,
                                                            @Param("fromDate") LocalDate fromDate,
                                                            @Param("toDate") LocalDate toDate);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM tb_schedule s WHERE s.doctorId = :doctorId AND s.date = :date AND s.time = :time")
    boolean scheduleIsOccupiedByDoctorId(Long doctorId, LocalDate date, LocalTime time);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM tb_schedule s WHERE s.personId = :personId AND s.date = :date AND s.time = :time")
    boolean scheduleIsOccupiedByPersonId(Long personId, LocalDate date, LocalTime time);

    @Modifying
    @Query(value = "INSERT INTO tb_schedule (doctor_id, person_id, specialty, date, time, appointmentType) " +
        "SELECT :doctorId, :personId, :specialty, :date, :time, :appointmentType " +
        "FROM Doctor d " +
        "JOIN Person p " +
        "WHERE d.id = :doctorId " +
        "AND p.id = :personId", nativeQuery = true)
    void save(@Param("doctorId") Long doctorId,
          @Param("personId") Long personId,
          @Param("specialty") String specialty,
          @Param("date") LocalDate date,
          @Param("time") LocalTime time,
          @Param("appointmentType") AppointmentType appointmentType);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_schedule " +
            "SET doctor_id = :doctorId, " +
            "person_id = :personId, " +
            "specialty = :specialty, " +
            "date = :date, " +
            "time = :time, " +
            "appointment_type = :appointmentType " +
            "WHERE schedule_id = :scheduleId", nativeQuery = true)
    void updateSchedule(@Param("scheduleId") Long scheduleId,
                   @Param("doctorId") Long doctorId,
                   @Param("personId") Long personId,
                   @Param("specialty") String specialty,
                   @Param("date") LocalDate date,
                   @Param("time") LocalTime time,
                   @Param("appointmentType") AppointmentType appointmentType);
}
