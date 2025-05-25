package com.example.healthcare.service.impl;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.helper.exceptions.AlreadyExistsException;
import com.example.healthcare.helper.exceptions.IDMismatchException;
import com.example.healthcare.helper.exceptions.NotFoundException;
import com.example.healthcare.helper.mapper.CustomAppointmentMapper;
import com.example.healthcare.helper.utils.StringExtractor;
import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.repository.StaffRepository;
import com.example.healthcare.security.CustomUserDetails;
import com.example.healthcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;


    @Override
    public void createNewAppointment(NewAppointmentRequestDTO request, Long userId, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        if(!userId.equals(currentUserId)){
            throw new IDMismatchException("User ID mismatch");
        }

        Appointment appointment = new Appointment();
        Optional<Patient> optionalPatient = patientRepository.findById(currentUserId);

        Optional<MedicalStaff> optionalStaff = staffRepository.findByDepartment(request.getDepartment());

        if(optionalPatient.isPresent() && optionalStaff.isPresent()) {
            if(appointmentRepository.findByAppointmentDateAndTimeAndStaffId(request.getDateAndTime(), optionalStaff.get().getId()).isPresent()) {
                throw new AlreadyExistsException("There is another appointment booked at this time, try a different time.");
            } else {
                appointment.setAppointmentDateAndTime(request.getDateAndTime());
                appointment.setAppointmentNotes(request.getAppointmentNotes());
                appointment.setPatient(optionalPatient.get());
                appointment.setMedicalStaff(optionalStaff.get());
                appointment.setCreatedAt(LocalDate.now());
                appointment.setAppointmentStatus(Appointment.AppointmentStatus.REQUESTED);
                log.info("Saving new appointment request...");
                appointmentRepository.save(appointment);
            }
        } else {
            throw new NotFoundException("The user or medical staff does not exist.");
        }
    }

}
