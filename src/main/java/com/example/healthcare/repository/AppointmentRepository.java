package com.example.healthcare.repository;

import com.example.healthcare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * FROM appointment WHERE appointment_date_and_time=:dateTime AND staff_id=:id LIMIT 1", nativeQuery = true)
    Optional<Appointment> findByAppointmentDateAndTimeAndStaffId(LocalDateTime dateTime, Long id);

}
