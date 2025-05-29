package com.example.healthcare.model.medicalRecord;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordResponseDTO {

    private String diagnosis;
    private String treatmentPlan;
    private String notes;
    private LocalDate appointmentDate;
    private String overseeingDoctor;
    
}