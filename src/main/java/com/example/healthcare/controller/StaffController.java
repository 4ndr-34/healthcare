package com.example.healthcare.controller;

import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.prescription.PrescriptionRequestDTO;
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



    //APPOINTMENTS
    @GetMapping("/appointments")
    @PreAuthorize("hasRoles('STAFF','DOCTOR','ADMIN')")
    public String appointmentsPage (Model model, @RequestParam(name = "date", required = false) LocalDate selectedDate, Authentication authentication) {
        System.out.println("selectedDate: " + selectedDate);
        if(selectedDate == null && !isDateInRequest()) {
            selectedDate = LocalDate.now();
        }

        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute( "userAppointments", staffService.getAppointmentsForStaff(authentication, selectedDate));
        return "staff/appointments";
    }

    private boolean isDateInRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getParameterMap()
                .containsKey("date");
    }



    //PRESCRIPTIONS
    @GetMapping("/appointments/newprescription/{patientId}/{appointmentId}")
    public String newPrescription(@PathVariable("patientId") Long patientId, @PathVariable("appointmentId") Long appointmentId, Model model) {
        model.addAttribute("patientId", patientId);
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("prescription", new PrescriptionRequestDTO());
        return "staff/new-prescription";
    }

    @PostMapping("/appointments/newprescription/{patientId}/{appointmentId}")
    public String newPrescription(@PathVariable("patientId") Long patientId,@PathVariable("appointmentId") Long appointmentId, @ModelAttribute("request") PrescriptionRequestDTO request) {
        int success = staffService.createPrescription(request, patientId, appointmentId);
        if (success == 1) {
            return "redirect:/staff/prescription/newprescription-success";
        } else {
            return "redirect:/staff/prescription/newprescription-success";
        }
    }

    @GetMapping("/prescription/newprescription-success")
    public String newPrescriptionSuccess() {
        return "staff/new-prescription-success";
    }

    @GetMapping("/prescription/newprescription-failed")
    public String newPrescriptionFailed() {
        return "staff/new-prescription-failed";
    }
}
