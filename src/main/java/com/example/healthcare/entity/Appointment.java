package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentNotes;
    @Enumerated(EnumType.STRING)
    private MedicalStaff.MedicalDepartment medicalDepartment;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    private LocalDate createdAt;
    private LocalDate lastUpdated;

    //foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", referencedColumnName = "id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="staff_id", referencedColumnName = "id")
    private MedicalStaff medicalStaff;
    @OneToOne
    @JoinColumn(name = "medical_record_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private List<Prescription> prescriptions;
    @OneToOne
    private Billing billing;
    public enum AppointmentStatus {
        REQUESTED,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
    }
}
