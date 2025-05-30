package com.example.healthcare.helper.mapper;

import com.example.healthcare.entity.MedicalRecord;
import com.example.healthcare.model.medicalRecord.MedicalRecordResponseDTO;

public class CustomMedicalRecordMapper {

    public static MedicalRecordResponseDTO toMedicalRecordResponseDTO(MedicalRecord medicalRecord) {
        MedicalRecordResponseDTO response = new MedicalRecordResponseDTO();
        response.setDiagnosis(medicalRecord.getDiagnosis());
        response.setNotes(medicalRecord.getNotes());
        response.setAppointmentDate(medicalRecord.getAppointment().getAppointmentDate());
        response.setTreatmentPlan(medicalRecord.getTreatmentPlan());
        response.setOverseeingDoctor(medicalRecord.getMedicalStaff());
        return response;
    }

}
