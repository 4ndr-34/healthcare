package com.example.healthcare.service.impl;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.StaffRepository;
import com.example.healthcare.security.CustomUserDetails;
import com.example.healthcare.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentResponseDTO> getAppointmentsForStaff(Authentication authentication, LocalDate appointmentDate) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        //List<Appointment> staffAppointments = appointmentRepository.findByAppointmentDateAndTimeAndStaffId()

        Optional<MedicalStaff> optionalStaff = staffRepository.findById(currentUserId);
        if (optionalStaff.isPresent()) {

        }
        return null;
    }
}
