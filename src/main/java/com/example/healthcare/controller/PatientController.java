package com.example.healthcare.controller;

import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.impl.AppointmentServiceImpl;
import com.example.healthcare.service.impl.PatientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import java.util.List;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;
    private final AppointmentServiceImpl appointmentService;

    @GetMapping("/login-register")
    public String registerPatientHandler(Model model) {
        model.addAttribute("request", new RegisterUserRequestDTO());
        return "login-register/login-register";
    }


    @GetMapping("/home")
    @PreAuthorize("hasRole('PATIENT')")
    public String homePage() {
        return "patient/home";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute LoginRequestDTO request) {
        patientService.patientLogin(request, null, null);
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute RegisterUserRequestDTO request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Now you can login");
        patientService.registerPatient(request);
        return "redirect:/patient/successful-registration";
    }

    @GetMapping("/successful-registration")
    public String successfulRegistrationPage() {
        return "login-register/successful-registration";
    }

/*    @GetMapping("/test-security-context")
    public String testSecurityContext(Authentication authentication) {
        System.out.println("Current authorities: " + authentication.getAuthorities());
        return "Current roles: " + authentication.getAuthorities();
    }

    @GetMapping("/check-session")
    public String checkSession(HttpSession session, Authentication auth) {
        return "Session ID: " + session.getId() +
                "<br>Authorities: " + auth.getAuthorities();
    }*/

    @GetMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public String appointmentsPage( Model model, Authentication authentication) {
        model.addAttribute("userAppointments", patientService.getAppointmentsOfPatient(authentication));
        return "patient/appointments";
    }

    @GetMapping("/appointment/new")
    @PreAuthorize("hasRole('PATIENT')")
    public String newAppointmentPage(Model model) {
        model.addAttribute("request", new NewAppointmentRequestDTO());
        return "patient/new-appointment";
    }

    @PostMapping("/appointment/new")
    @PreAuthorize("hasRole('PATIENT')")
    public String createNewAppointment(@ModelAttribute NewAppointmentRequestDTO request, @RequestParam Long userId, Authentication authentication) {
        appointmentService.createNewAppointment(request, userId, authentication);
        return "redirect:/patient/appointments";
    }



}
