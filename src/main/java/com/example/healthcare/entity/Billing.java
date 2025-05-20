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
    private LocalDate dueDate;
    private BillingStatus status;
    private Long insuranceClaimId;
    private Double insuranceCoverage;
    private Double patientResponsibility;
    private LocalDate paymentDate;
    private String paymentMethod;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    //foreign keys
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="patient_id")
    private Patient patient;


    public enum BillingStatus {
        PENDING,
        PAID,
        INSURANCE_CLAIM,
        OVERDUE,
        WRITE_OFF
    }

}
