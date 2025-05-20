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
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;
    private LocalDate prescribedDate;
    private Integer refills;
    private PrescriptionStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    //foreign keys
    @ManyToOne
    private Appointment appointment;



    public enum PrescriptionStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED,
        CANCELLED
    }
}
