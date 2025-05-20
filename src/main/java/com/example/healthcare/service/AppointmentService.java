package com.example.healthcare.service;

import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;

public interface AppointmentService {

    NewAppointmentResponseDTO createNewAppointment(NewAppointmentRequestDTO newAppointmentRequestDTO);

}
