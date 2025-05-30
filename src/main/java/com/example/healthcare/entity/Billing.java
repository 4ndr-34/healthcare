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
    private String insuranceClaimId;//change to String
    private String paymentMethod;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    //foreign keys
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name="appointment_id")
    private Appointment appointment;


}
