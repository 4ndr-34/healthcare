package com.example.healthcare.model.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewAppointmentResponseDTO {


    @NotNull
    private LocalDateTime dateAndTime;
    @NotNull
    private String department;
    @NotNull
    private String doctorName;
    @NotNull
    private String patientName;
    @NotNull
    private String status;

}
