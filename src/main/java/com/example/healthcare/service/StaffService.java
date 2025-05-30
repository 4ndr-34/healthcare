package com.example.healthcare.service;

import com.example.healthcare.model.billing.BillingDTO;
import com.example.healthcare.model.prescription.PrescriptionDTO;


import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.medicalRecord.MedicalRecordRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface StaffService {

    public int createPrescription(PrescriptionDTO request, Long patientId, Long appointmentId);

    public int createBilling(BillingDTO request, Long appointmentId, Long patientId);

    void staffLogin(LoginRequestDTO authRequest, HttpServletRequest request, HttpServletResponse response);

    List<AppointmentResponseDTO> getAppointmentsForStaff(Authentication authentication, LocalDate appointmentDate);

    public int createMedicalRecord(MedicalRecordRequestDTO request, Long appointmentId, Long patientId, Authentication authentication);
    
}
