package com.example.healthcare.controller;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.impl.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final AppointmentServiceImpl appointmentService;

    @PostMapping("/create")
    public SuccessfulRegisterDTO createNewStaffMember(@RequestBody RegisterUserRequestDTO registerUserRequestDTO){
        return null;
    }

    @PutMapping("/appointments/{appointmentId}/approve")
    public ResponseEntity<String> approveAppointment(@PathVariable Long appointmentId) {
        appointmentService.updateAppointmentStatus(appointmentId, Appointment.AppointmentStatus.CONFIRMED);
        return ResponseEntity.ok("Appointment approved.");
    }

    @PutMapping("/appointments/{appointmentId}/decline")
    public ResponseEntity<String> declineAppointment(@PathVariable Long appointmentId) {
        appointmentService.updateAppointmentStatus(appointmentId, Appointment.AppointmentStatus.CANCELLED);
        return ResponseEntity.ok("Appointment declined.");
    }

}
