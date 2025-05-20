package com.example.healthcare.service.impl;

import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.service.AppointmentService;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public NewAppointmentResponseDTO createNewAppointment(NewAppointmentRequestDTO newAppointmentRequestDTO) {
        return null;
    }
}
