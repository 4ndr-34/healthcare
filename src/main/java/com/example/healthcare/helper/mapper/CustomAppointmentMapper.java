package com.example.healthcare.helper.mapper;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;

public class CustomAppointmentMapper {

    /*public static NewAppointmentResponseDTO toNewAppointmentResponseDTO(Appointment appointment) {
        NewAppointmentResponseDTO response = new NewAppointmentResponseDTO();
        response.setDateAndTime(appointment.getAppointmentDateAndTime());
        response.setDoctorName(appointment.getMedicalStaff().getFirstName() + " " + appointment.getMedicalStaff().getLastName());
        response.setDepartment(appointment.getMedicalStaff().getDepartment().name());
        response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        response.setStatus(appointment.getAppointmentStatus().name());
        return response;
    }*/

    public static AppointmentResponseDTO toAppointmentResponseDTO(Appointment appointment) {

        AppointmentResponseDTO response = new AppointmentResponseDTO();

        response.setId(appointment.getId());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setAppointmentNotes(appointment.getAppointmentNotes());
        response.setAppointmentStatus(appointment.getAppointmentStatus().name());
        response.setCreatedAt(appointment.getCreatedAt().atStartOfDay());
        response.setPatientId(appointment.getPatient().getId());
        if(appointment.getMedicalRecord() != null) {
            response.setMedicalRecordId(appointment.getMedicalRecord().getId());
        }
        return response;
    }

}
