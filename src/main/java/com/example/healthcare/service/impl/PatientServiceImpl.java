package com.example.healthcare.service.impl;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.helper.exceptions.NotFoundException;
import com.example.healthcare.helper.mapper.CustomAppointmentMapper;
import com.example.healthcare.helper.mapper.CustomUserMapper;
import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public SuccessfulRegisterDTO registerPatient(RegisterUserRequestDTO registerUserRequestDTO) {
        try {
            patientRepository.save(CustomUserMapper.mapRegisterDTOToPatient(registerUserRequestDTO));
            return new SuccessfulRegisterDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsOfPatient(Long patientId) {
        if(!patientRepository.existsById(patientId)) {
            throw new NotFoundException("Patient with ID: " + patientId + " does not exist.");
        }

        List<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId);

        return appointments.stream().map(CustomAppointmentMapper::toAppointmentResponseDTO).toList();
    }


}
