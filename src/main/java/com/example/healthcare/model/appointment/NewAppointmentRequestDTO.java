package com.example.healthcare.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class NewAppointmentRequestDTO {

    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateAndTime;
    private String department;
    private String appointmentNotes;
    private String medicalStaffName;



}
