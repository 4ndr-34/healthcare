package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDate billingDate;
    private LocalDate dueDate;//remove
    private BillingStatus status;//remove
    private String insuranceClaimId;//change to String
    private Double insuranceCoverage;//remove
    private Double patientResponsibility;//remove
    private LocalDate paymentDate;//remove
    private String paymentMethod;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    //foreign keys
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="patient_id")
    private Patient patient;

    //remove
    public enum BillingStatus {
        PENDING,
        PAID,
        INSURANCE_CLAIM,
        OVERDUE,
        WRITE_OFF
    }

}
