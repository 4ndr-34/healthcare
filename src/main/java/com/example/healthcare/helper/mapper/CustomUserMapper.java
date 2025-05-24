package com.example.healthcare.helper.mapper;


import com.example.healthcare.entity.Patient;
import com.example.healthcare.entity.User;
import com.example.healthcare.model.register.RegisterUserRequestDTO;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CustomUserMapper {

    public static Patient mapRegisterDTOToPatient(RegisterUserRequestDTO request) {
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setBirthDate(request.getBirthDate());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setCity(request.getCity());
        patient.setCountry(request.getCountry());
        if(request.getZipCode() != null) {
            patient.setZipCode(request.getZipCode());
        }
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());
        patient.setPassword(request.getPassword());
        patient.setCreatedAt(LocalDate.now());
        if (request.getInsuranceProvider()!= null) {
            patient.setInsuranceProvider(request.getInsuranceProvider());
        }
        if (request.getInsurancePolicyNumber()!= null) {
            patient.setInsurancePolicyNumber(request.getInsurancePolicyNumber());
        }
        if (request.getEmergencyContactName()!= null) {
            patient.setEmergencyContactName(request.getEmergencyContactName());
        }
        if (request.getEmergencyContactPhone()!= null) {
            patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        }
        patient.setRole(User.UserRoles.PATIENT);
       return patient;
    }

}
