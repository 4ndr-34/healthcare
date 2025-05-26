package com.example.healthcare.controller;

import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.impl.AppointmentServiceImpl;
import com.example.healthcare.service.impl.PatientServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/login")
    public String patientLogin(@ModelAttribute LoginRequestDTO request) {
        //patientService.patientLogin(request);
        return "redirect:/patient/home";
    }

    @GetMapping("/home")
    public String successfulLogin() {
        return "patient/home";
    }



    @PostMapping("/register")
    public String registerPatient(@ModelAttribute RegisterUserRequestDTO request, RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Now you can login");
        patientService.registerPatient(request);
        return "redirect:/patient/successful-registration";
    }

    @GetMapping("/successful-registration")
    public String successfulRegistrationPage() {
        return "login-register/successful-registration";
    }





    @PostMapping("/newappointment")
    public ResponseEntity<NewAppointmentResponseDTO> createNewAppointment(@RequestBody NewAppointmentRequestDTO newAppointmentRequestDTO) {
        return new ResponseEntity<>(appointmentService.createNewAppointment(newAppointmentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getPatientAppointments(@RequestParam Long patientId) {
        List<AppointmentResponseDTO> appointments = patientService.getAppointmentsOfPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }


}
