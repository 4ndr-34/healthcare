package com.example.healthcare.service.impl;

import com.example.healthcare.helper.mapper.CustomUserMapper;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl {

    private final PatientRepository patientRepository;

    public SuccessfulRegisterDTO registerPatient(RegisterUserRequestDTO registerUserRequestDTO) {
        try {
            patientRepository.save(CustomUserMapper.mapRegisterDTOToPatient(registerUserRequestDTO));
            return new SuccessfulRegisterDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
