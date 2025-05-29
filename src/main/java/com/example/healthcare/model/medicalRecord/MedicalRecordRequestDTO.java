package com.example.healthcare.model.medicalRecord;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MedicalRecordRequestDTO{

    private String diagnosis;
    private String treatmentPlan;
    private String notes;

}