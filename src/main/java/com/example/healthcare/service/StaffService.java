package com.example.healthcare.service;

import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface StaffService {

    void staffLogin(LoginRequestDTO authRequest, HttpServletRequest request, HttpServletResponse response);

    List<AppointmentResponseDTO> getAppointmentsForStaff(Authentication authentication, LocalDate appointmentDate);

}
