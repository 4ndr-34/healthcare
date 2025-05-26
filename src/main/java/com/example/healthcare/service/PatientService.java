package com.example.healthcare.service;


import com.example.healthcare.model.appointment.AppointmentResponseDTO;

import java.util.List;

public interface PatientService {
    List<AppointmentResponseDTO> getAppointmentsOfPatient(Long patientId);
}
