package com.example.healthcare.controller;

import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.service.impl.StaffServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffServiceImpl staffService;

    @GetMapping("/login")
    public String staffLogin() {
        return "staff/login";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute LoginRequestDTO request) {
        staffService.staffLogin(request, null, null);
    }

    @GetMapping("/home")
    public String home() {
        return "staff/home";
    }

    @GetMapping("/appointments")
    @PreAuthorize("hasRoles('STAFF','DOCTOR','ADMIN')")
    public String appointmentsPage (Model model, @RequestParam(name = "date", required = false) LocalDate selectedDate, Authentication authentication) {
        System.out.println("selectedDate: " + selectedDate);
        if(selectedDate == null && !isDateInRequest()) {
            selectedDate = LocalDate.now();
        }

        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("userAppointments", staffService.getAppointmentsForStaff(authentication, selectedDate));
        return "staff/appointments";
    }

    private boolean isDateInRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getParameterMap()
                .containsKey("date");
    }
}
