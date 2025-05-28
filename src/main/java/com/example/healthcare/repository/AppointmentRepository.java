package com.example.healthcare.repository;

import com.example.healthcare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * FROM appointment WHERE appointment_date=:date AND appointment_time =:time AND staff_id=:id LIMIT 1", nativeQuery = true)
    Optional<Appointment> findByAppointmentDateAndTimeAndStaffId(LocalDate date, LocalTime time, Long id);

    List<Appointment> findAllByPatientId(Long patientId);


    List<Appointment> findAllByAppointmentDateAndMedicalStaffId(LocalDate appointmentDate, Long id);

}
