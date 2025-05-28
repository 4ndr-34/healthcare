package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medication;
    private String instructions;
    private LocalDate prescribedDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    //foreign keys
    @ManyToOne
    private Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    public enum PrescriptionStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED,
        CANCELLED
    }
}
