package com.example.healthcare.model.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Data
public class RegisterStaffRequestDTO {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    @Pattern(regexp="[MF]", message = "Allowed Values are M or F")
    private char gender;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String country;
    private String zipCode;
    @NotNull
    private String phoneNumber;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String specialty;
    @NotNull
    private String licenseNumber;
    private String Department;
    @NotNull
    private String role;
    @NotNull
    private Boolean active;
}
