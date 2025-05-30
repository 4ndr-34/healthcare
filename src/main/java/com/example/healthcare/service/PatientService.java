package com.example.healthcare.service;


import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.billing.BillingDTO;
import com.example.healthcare.model.medicalRecord.MedicalRecordResponseDTO;
import com.example.healthcare.model.prescription.PrescriptionDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PatientService {
    List<AppointmentResponseDTO> getPatientAppointments(Authentication authentication);
    List<PrescriptionDTO> getPatientPrescriptions(Authentication authentication);
    List<BillingDTO> getPatientBills(Authentication authentication);
    List<MedicalRecordResponseDTO> getPatientMedicalRecords(Authentication authentication);
}
