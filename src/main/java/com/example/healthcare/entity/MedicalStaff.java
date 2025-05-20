package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "staff")
@Data
public class MedicalStaff extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specialty;
    @Column(unique = true)
    private String licenseNumber;
    private String Department;
    @Enumerated(EnumType.STRING)
    private User.UserRoles role;
    private Boolean active;


    //foreign keys
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalStaff", cascade = CascadeType.MERGE)
    private List<Appointment> appointments;


}


