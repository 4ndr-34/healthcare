package com.example.healthcare.service.impl;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.helper.exceptions.AlreadyExistsException;
import com.example.healthcare.helper.exceptions.NotFoundException;
import com.example.healthcare.helper.mapper.CustomAppointmentMapper;
import com.example.healthcare.helper.utils.StringExtractor;
import com.example.healthcare.model.appointment.NewAppointmentRequestDTO;
import com.example.healthcare.model.appointment.NewAppointmentResponseDTO;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.repository.StaffRepository;
import com.example.healthcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public NewAppointmentResponseDTO createNewAppointment(NewAppointmentRequestDTO request) {

        Appointment appointment = new Appointment();
        Optional<Patient> optionalPatient = patientRepository.findById(request.getUserId());
        List<String> name = StringExtractor.extractFirstAndLastName(request.getMedicalStaffName());
        Optional<MedicalStaff> optionalStaff = staffRepository.findByDepartmentAndFirstNameAndLastName(request.getDepartment(), name.get(0), name.get(1));

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
                return CustomAppointmentMapper.toNewAppointmentResponseDTO(appointmentRepository.save(appointment));
            }
        } else {
            throw new NotFoundException("The user or medical staff does not exist.");
        }
    }

}
