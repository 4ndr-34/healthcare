package com.example.healthcare.model.prescription;

import lombok.Data;
import java.time.LocalDate;


@Data
public class PrescriptionRequestDTO {

    private String medication;
    private String instructions;
    private LocalDate prescribedDate;
    private Long appointmentId;
}