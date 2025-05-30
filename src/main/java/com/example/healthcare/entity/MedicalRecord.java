package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnosis;
    private String treatmentPlan;
    private String notes;
    private LocalDate createdAt;
    private LocalDate lastUpdated;
    private String medicalStaff;
    
    //foreign keys
    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
