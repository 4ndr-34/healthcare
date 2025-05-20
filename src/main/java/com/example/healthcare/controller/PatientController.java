package com.example.healthcare.controller;

import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.impl.AppointmentServiceImpl;
import com.example.healthcare.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;
    private final AppointmentServiceImpl appointmentService;

    @PostMapping("/register")
    public ResponseEntity<SuccessfulRegisterDTO> registerUser(RegisterUserRequestDTO registerUserRequestDTO) {
        return new ResponseEntity<>(patientService.registerPatient(registerUserRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/newappointment")
    public ResponseEntity<NewAppointmentResponseDTO> createNewAppointment(NewAppointmentRequestDTO newAppointmentRequestDTO) {
        return new ResponseEntity<>(appointmentService.createNewAppointment(newAppointmentRequestDTO), HttpStatus.CREATED);
    }

}
