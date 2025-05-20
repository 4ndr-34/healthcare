package com.example.healthcare.helper.utils;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentUtils {

    private final AppointmentRepository appointmentRepository;

    public NewAppointmentResponseDTO createNewAppointment(NewAppointmentRequestDTO request) {
        NewAppointmentResponseDTO response = new NewAppointmentResponseDTO();
        Appointment appointment = new Appointment();
        appointment.setAppointmentDateAndTime(request.getDateAndTime());
        appointment.setAppointmentNotes(request.getAppointmentNotes());
        appointment.setMedicalStaff(new MedicalStaff());
        return response;

    }

}
