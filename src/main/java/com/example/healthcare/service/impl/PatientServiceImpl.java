package com.example.healthcare.service.impl;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.Billing;
import com.example.healthcare.entity.MedicalRecord;
import com.example.healthcare.entity.Prescription;
import com.example.healthcare.helper.exceptions.NotFoundException;
import com.example.healthcare.helper.mapper.*;
import com.example.healthcare.model.billing.BillingDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.medicalRecord.MedicalRecordResponseDTO;
import com.example.healthcare.model.prescription.PrescriptionDTO;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.repository.*;
import com.example.healthcare.security.CustomUserDetails;
import com.example.healthcare.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final BillingRepository billingRepository;

    public SuccessfulRegisterDTO registerPatient(RegisterUserRequestDTO registerUserRequestDTO) {
        try {
            patientRepository.save(CustomUserMapper.mapRegisterDTOToPatient(registerUserRequestDTO));
            return new SuccessfulRegisterDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void patientLogin(LoginRequestDTO authRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.getUsername(), authRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);

            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
        }
    }

    /*public void newAppointment(NewAppointmentRequestDTO request, Long userId, Principal principal, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();
        Appointment appointment = new Appointment();

        if (!userId.equals(currentUserId)) {
            throw new BadCredentialsException("Invalid user");
        }

        appointment.setPatient(patientRepository.getPatientById(userId));


    }*/


    @Override
    public List<AppointmentResponseDTO> getPatientAppointments(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();


        if(!patientRepository.existsById(currentUserId)) {
            throw new NotFoundException("Patient with ID: " + currentUserId + " does not exist.");
        } else {
            List<Appointment> appointments = appointmentRepository.findAllByPatientId(currentUserId);
            return appointments.stream().map(CustomAppointmentMapper::toAppointmentResponseDTO).toList();
        }
    }

    @Override
    public List<PrescriptionDTO> getPatientPrescriptions(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();


        if(!patientRepository.existsById(currentUserId)) {
            throw new NotFoundException("Patient with ID: " + currentUserId + " does not exist.");
        } else {
            List<Prescription> prescriptions = prescriptionRepository.findAllByPatientId(currentUserId);
            return prescriptions.stream().map(CustomPrescriptionMapper::toPrescriptionDTO).toList();
        }
    }

    @Override
    public List<BillingDTO> getPatientBills(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        if(!patientRepository.existsById(currentUserId)) {
            throw new NotFoundException("Patient with ID: " + currentUserId + " does not exist.");
        } else {
            List<Billing> bills = billingRepository.findAllByPatientId(currentUserId);
            return bills.stream().map(CustomBillingMapper::toBillingDTO).toList();
        }
    }

    @Override
    public List<MedicalRecordResponseDTO> getPatientMedicalRecords(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();


        if(!patientRepository.existsById(currentUserId)) {
            throw new NotFoundException("Patient with ID: " + currentUserId + " does not exist.");
        } else {
            List<MedicalRecord> medicalRecords = medicalRecordRepository.getAllByPatientId(currentUserId);
            return medicalRecords.stream().map(CustomMedicalRecordMapper::toMedicalRecordResponseDTO).toList();
        }

    }


}
