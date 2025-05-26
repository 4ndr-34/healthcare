package com.example.healthcare.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class NewAppointmentRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
    @JsonFormat(pattern = "HH:mm")
    LocalTime appointmentTime;
    private String department;
    private String appointmentNotes;

}
