package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Patient extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String insuranceProvider;
    @Enumerated(EnumType.STRING)
    private User.UserRoles role;
    @Column(unique=true)
    private String insurancePolicyNumber;
    private String emergencyContactName;
    private String emergencyContactPhone;

    //foreign keys
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.PERSIST)
    private List<Appointment> appointments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.PERSIST)
    private List<Billing> billings;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.PERSIST)
    private List<Prescription> prescriptions;


}
