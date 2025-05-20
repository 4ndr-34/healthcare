package com.example.healthcare.helper.mapper;


import com.example.healthcare.entity.Patient;
import com.example.healthcare.entity.User;
import com.example.healthcare.model.register.RegisterUserRequestDTO;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CustomUserMapper {

    public static Patient mapRegisterDTOToPatient(RegisterUserRequestDTO registerUserRequestDTO) {
        Patient patient = new Patient();
        patient.setFirstName(registerUserRequestDTO.getFirstName());
        patient.setLastName(registerUserRequestDTO.getLastName());
        patient.setBirthDate(registerUserRequestDTO.getBirthDate());
        patient.setGender(registerUserRequestDTO.getGender());
        patient.setAddress(registerUserRequestDTO.getAddress());
        patient.setCity(registerUserRequestDTO.getCity());
        patient.setCountry(registerUserRequestDTO.getCountry());
        if(registerUserRequestDTO.getZipCode() != null) {
            patient.setZipCode(registerUserRequestDTO.getZipCode());
        }
        patient.setPhoneNumber(registerUserRequestDTO.getPhoneNumber());
        patient.setEmail(registerUserRequestDTO.getEmail());
        patient.setPassword(registerUserRequestDTO.getPassword());
        patient.setCreatedAt(LocalDate.parse(DateTimeFormatter.ofPattern("YYYY-MM-DD").format(LocalDate.now())));
        if (patient.getInsuranceProvider()!= null) {
            patient.setInsuranceProvider(registerUserRequestDTO.getInsuranceProvider());
        }
        if (patient.getInsurancePolicyNumber()!= null) {
            patient.setInsurancePolicyNumber(registerUserRequestDTO.getInsurancePolicyNumber());
        }
        if (patient.getEmergencyContactName()!= null) {
            patient.setEmergencyContactName(registerUserRequestDTO.getEmergencyContactName());
        }
        if (patient.getEmergencyContactPhone()!= null) {
            patient.setEmergencyContactPhone(registerUserRequestDTO.getEmergencyContactPhone());
        }
        patient.setRole(User.UserRoles.PATIENT);
        return patient;
    }

}
