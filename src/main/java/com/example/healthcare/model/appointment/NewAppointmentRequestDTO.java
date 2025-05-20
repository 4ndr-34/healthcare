package com.example.healthcare.model.appointment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewAppointmentRequestDTO {

    private LocalDateTime dateAndTime;
    private String appointmentNotes;
    private String medicalStaffName;



}
