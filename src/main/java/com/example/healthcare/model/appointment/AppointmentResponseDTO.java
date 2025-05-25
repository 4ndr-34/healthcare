package com.example.healthcare.model.appointment;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime appointmentDateTime;
    private String appointmentNotes;
    private String appointmentStatus;
    private LocalDateTime createdAt;
}