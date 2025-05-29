package com.example.healthcare.service;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface AppointmentService {

    void createNewAppointment(NewAppointmentRequestDTO request, Long userId, Authentication authentication);

    void updateAppointmentStatus(Long appointmentId, Appointment.AppointmentStatus status);

}
