package com.example.healthcare.helper.mapper;

import com.example.healthcare.entity.Prescription;
import com.example.healthcare.model.prescription.PrescriptionDTO;

public class CustomPrescriptionMapper {


    public static PrescriptionDTO toPrescriptionDTO(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setMedication(prescription.getMedication());
        prescriptionDTO.setInstructions(prescription.getInstructions());
        prescriptionDTO.setPrescribedDate(prescription.getPrescribedDate());
        prescriptionDTO.setAppointmentId(prescription.getAppointment().getId());
        return prescriptionDTO;
    }

}
