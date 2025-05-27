package com.example.healthcare.controller;

import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.impl.StaffServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffServiceImpl staffService;

    @PostMapping("/create")
    public SuccessfulRegisterDTO createNewStaffMember(@RequestBody RegisterUserRequestDTO registerUserRequestDTO){
        return null;
    }

    @GetMapping
    @PreAuthorize("hasRoles('STAFF','DOCTOR')")
    public String appointmentsPage (Model model, @RequestParam LocalDate requestedDate, Authentication authentication) {
        model.addAttribute("requestedDate", requestedDate);
        model.addAttribute("userAppointments", staffService.getAppointmentsForStaff(authentication, requestedDate));
        return "staff/appointments";
    }
}
