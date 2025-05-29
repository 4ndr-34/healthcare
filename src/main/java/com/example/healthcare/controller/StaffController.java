package com.example.healthcare.controller;

import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.prescription.PrescriptionDTO;
import com.example.healthcare.service.impl.StaffServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



import com.example.healthcare.model.billing.BillingRequestDTO;
import com.example.healthcare.entity.Appointment;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.impl.AppointmentServiceImpl;

import org.springframework.http.ResponseEntity;


import java.time.LocalDate;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffServiceImpl staffService;
    private final AppointmentServiceImpl appointmentService;

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
    @PreAuthorize("hasRoles('DOCTOR')")
    public String newPrescription(@PathVariable("patientId") Long patientId, @PathVariable("appointmentId") Long appointmentId, Model model) {
        model.addAttribute("patientId", patientId);
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("prescription", new PrescriptionDTO());
        return "staff/new-prescription";
    }



    @PostMapping("/appointments/newprescription/{patientId}/{appointmentId}")
    @PreAuthorize("hasRoles('DOCTOR')")
    public String newPrescription(@PathVariable("patientId") Long patientId,@PathVariable("appointmentId") Long appointmentId, @ModelAttribute("request") PrescriptionDTO request) {
        int success = staffService.createPrescription(request, patientId, appointmentId);
        if (success == 1) {
            return "redirect:/staff/prescription/newprescription-success";
        } else {
            return "redirect:/staff/prescription/newprescription-success";
        }
    }

    @GetMapping("/prescription/newprescription-success")
    @PreAuthorize("hasRoles('DOCTOR')")
    public String newPrescriptionSuccess() {
        return "staff/new-prescription-success";
    }

    @GetMapping("/prescription/newprescription-failed")
    @PreAuthorize("hasRoles('DOCTOR')")
    public String newPrescriptionFailed() {
        return "staff/new-prescription-failed";
    }

    @GetMapping("/appointments/new-bill/{patientId}/{appointmentId}")
    @PreAuthorize("hasRoles('STAFF')")
    public String newBill(@PathVariable("patientId") Long patientId, @PathVariable("appointmentId") Long appointmentId, Model model) {
        model.addAttribute("patientId", patientId);
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("bill", new BillingRequestDTO());
        return "staff/new-bill";
    }


    @PostMapping("/appointments/new-bill/{patientId}/{appointmentId}")
    @PreAuthorize("hasRoles('STAFF')")
    public String newBill(@PathVariable("patientId") Long patientId, @PathVariable("appointmentId") Long appointmentId, @ModelAttribute("bill") BillingRequestDTO request) {
        int success = staffService.createBilling(request, appointmentId, patientId);
        if (success == 1){
            return "redirect:/staff/new-bill-success";
        } else {
            return "redirect:/staff/new-bill-failed";
        }

    }

    @GetMapping("/new-bill-success")
    @PreAuthorize("hasRoles('STAFF')")
    public String newBillSuccess() {
        return "/staff/new-bill-success";
    }

    @GetMapping("/new-bill-failed")
    @PreAuthorize("hasRoles('STAFF')")
    public String newBillFailed() {
        return "/staff/new-bill-failed";
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
