package com.example.healthcare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDate;

@MappedSuperclass
@Data
public class User {
    @Column
    String firstName;
    @Column
    String lastName;
    @Column
    LocalDate birthDate;
    @Column
    char gender;
    @Column
    String address;
    @Column
    String city;
    @Column
    String country;
    @Column
    String zipCode;
    @Column(unique=true)
    String phoneNumber;
    @Column(unique=true)
    String email;
    @Column
    String password;
    @Column
    LocalDate createdAt;
    @Column
    LocalDate lastUpdated;

    public enum UserRoles {
        PATIENT, DOCTOR, STAFF, ADMIN
    }
}
