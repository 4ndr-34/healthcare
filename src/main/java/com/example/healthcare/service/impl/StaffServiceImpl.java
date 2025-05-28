package com.example.healthcare.service.impl;


import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.helper.exceptions.NotFoundException;
import com.example.healthcare.helper.mapper.CustomAppointmentMapper;
import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.StaffRepository;
import com.example.healthcare.security.CustomUserDetails;
import com.example.healthcare.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final AppointmentRepository appointmentRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository;
    @Override
    public void staffLogin(LoginRequestDTO authRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.getUsername(), authRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);

            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public List<AppointmentResponseDTO> getAppointmentsForStaff(Authentication authentication, LocalDate appointmentDate) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        //List<Appointment> staffAppointments = appointmentRepository.findByAppointmentDateAndTimeAndStaffId()

        if (!staffRepository.existsById(currentUserId)) {
            throw new NotFoundException("Staff with ID: " + currentUserId + " does not exist.");
        }
        else {
            List<Appointment> appointments = appointmentRepository.findAllByAppointmentDateAndMedicalStaffId(appointmentDate, currentUserId);
            return appointments.stream().map(CustomAppointmentMapper::toAppointmentResponseDTO).toList();
        }
    }
}
