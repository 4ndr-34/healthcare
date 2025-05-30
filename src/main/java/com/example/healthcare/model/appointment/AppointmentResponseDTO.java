package com.example.healthcare.model.appointment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentNotes;
    private String appointmentStatus;
    private LocalDateTime createdAt;
    private Long patientId;
    private Long medicalRecordId;
}