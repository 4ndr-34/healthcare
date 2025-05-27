package com.example.healthcare.service;

import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface StaffService {

    public List<AppointmentResponseDTO> getAppointmentsForStaff(Authentication authentication, LocalDate appointmentDate);

}
