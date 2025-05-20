package com.example.healthcare.model.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Data
public class RegisterUserRequestDTO {

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
    private String insuranceProvider;
    private String insurancePolicyNumber;
    private String emergencyContactName;
    private String emergencyContactPhone;
}
