package com.example.healthcare.service;


import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.prescription.PrescriptionDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PatientService {
    List<AppointmentResponseDTO> getAppointmentsOfPatient(Authentication authentication);
    List<PrescriptionDTO> getPrescriptionsOfPatient(Authentication authentication);
}
