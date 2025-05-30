package com.example.healthcare.controller;

import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.service.impl.AppointmentServiceImpl;
import com.example.healthcare.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;
    private final AppointmentServiceImpl appointmentService;

    @GetMapping("/login-register")
    public String registerPatientHandler(Model model) {
        model.addAttribute("request", new RegisterUserRequestDTO());
        return "patient/login-register";
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
        return "patient/successful-registration";
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


    //APPOINTMENTS
    @GetMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public String appointmentsPage( Model model, Authentication authentication) {
        model.addAttribute("userAppointments", patientService.getPatientAppointments(authentication));
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


    @GetMapping("/prescriptions")
    @PreAuthorize("hasRole('PATIENT')")
    public String prescriptionsPage(Model model, Authentication authentication) {
        model.addAttribute("prescriptions", patientService.getPatientPrescriptions(authentication));
        return "patient/prescriptions";
    }

    @GetMapping("/bills")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientBills(Model model, Authentication authentication) {
        model.addAttribute("bills", patientService.getPatientBills(authentication));
        return "patient/bills";
    }

    @GetMapping("/medical-records")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientMedicalRecords(Model model, Authentication authentication) {
        model.addAttribute("medicalRecords", patientService.getPatientMedicalRecords(authentication));
        return "patient/medical-records";
    }

}
