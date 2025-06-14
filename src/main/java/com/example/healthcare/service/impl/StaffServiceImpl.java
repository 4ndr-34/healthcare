package com.example.healthcare.service.impl;


import com.example.healthcare.entity.Appointment;
import com.example.healthcare.helper.exceptions.NotFoundException;
import com.example.healthcare.helper.mapper.CustomAppointmentMapper;
import com.example.healthcare.model.appointment.AppointmentResponseDTO;
import com.example.healthcare.model.login.LoginRequestDTO;
import com.example.healthcare.model.medicalRecord.MedicalRecordRequestDTO;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.StaffRepository;
import com.example.healthcare.security.CustomUserDetails;
import com.example.healthcare.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.healthcare.entity.*;
import com.example.healthcare.model.billing.BillingDTO;
import com.example.healthcare.model.prescription.PrescriptionDTO;
import com.example.healthcare.repository.BillingRepository;
import com.example.healthcare.repository.MedicalRecordRepository;
import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.repository.PrescriptionRepository;


import java.lang.Long;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService{

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final BillingRepository billingRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository;
    private final StaffRepository staffRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public int createPrescription(PrescriptionDTO request, Long patientId, Long appointmentId){
        
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        
        
        if(!optionalAppointment.isPresent() || !optionalPatient.isPresent()) {
                        throw new NotFoundException("Appointment or User does not exist.");
        } else {
            //set appointment to completed
            optionalAppointment.get().setAppointmentStatus(Appointment.AppointmentStatus.COMPLETED);
            Appointment savedAppointment = appointmentRepository.save(optionalAppointment.get());

            Prescription prescription = new Prescription();
            prescription.setMedication(request.getMedication());
            prescription.setInstructions(request.getInstructions());
            prescription.setPrescribedDate(LocalDate.now());
            prescription.setCreatedAt(LocalDate.now());
            prescription.setAppointment(savedAppointment);
            prescription.setPatient(optionalPatient.get());
            prescription.setUpdatedAt(null);
            log.info("Saving new prescription...");
            prescriptionRepository.save(prescription);
            return 1;
        }
    }

    @Override
    public int createBilling(BillingDTO request, Long appointmentId, Long patientId) {
        if(!appointmentRepository.existsById(appointmentId) || !patientRepository.existsById(patientId)) {
                        throw new NotFoundException("Appointment or User does not exist.");
        } else {
            Billing billing = new Billing();
            billing.setAmount(request.getAmount());
            billing.setBillingDate(request.getBillingDate());
            billing.setPaymentMethod(request.getPaymentMethod());
            billing.setCreatedAt(LocalDate.now());
            billing.setPatient(patientRepository.findById(patientId).get());
            billing.setUpdatedAt(null);
            billingRepository.save(billing);
            return 1;
        }
    }

    @Override
    public void staffLogin(LoginRequestDTO authRequest, HttpServletRequest request, HttpServletResponse response) {
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


    @Override
    public List<AppointmentResponseDTO> getAppointmentsForStaff(Authentication authentication, LocalDate appointmentDate) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        //List<Appointment> staffAppointments = appointmentRepository.findByAppointmentDateAndTimeAndStaffId()

        if (!staffRepository.existsById(currentUserId)) {
            throw new NotFoundException("Staff with ID: " + currentUserId + " does not exist.");
        }
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STAFF"))) {
            List<Appointment> appointments = appointmentRepository.findAllByAppointmentDate(appointmentDate);
            return appointments.stream().map(CustomAppointmentMapper::toAppointmentResponseDTO).toList();
        }
        else {
            List<Appointment> appointments = appointmentRepository.findAllByAppointmentDateAndMedicalStaffId(appointmentDate, currentUserId);
            return appointments.stream().map(CustomAppointmentMapper::toAppointmentResponseDTO).toList();
        }
    }




    @Async("taskExecutor")
    public CompletableFuture<Optional<Appointment>> getAppointmentAsync(Long appointmentId) {
        return CompletableFuture.completedFuture(appointmentRepository.findById(appointmentId));
    }

    @Async("taskExecutor")
    public CompletableFuture<Optional<Patient>> getPatientAsync(Long patientId) {
        return CompletableFuture.completedFuture(patientRepository.findById(patientId));
    }

    @Async("taskExecutor")
    public CompletableFuture<Optional<MedicalStaff>> getStaffAsync(Long staffId) {
        return CompletableFuture.completedFuture(staffRepository.findById(staffId));
    }

    @Async("taskExecutor")
    public void saveMedicalRecordAsync(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }


/*    @Override
    public int createMedicalRecord(MedicalRecordRequestDTO request, Long appointmentId, Long patientId, Authentication authentication) {

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();
        Optional<MedicalStaff> optionalMedicalStaff = staffRepository.findById(currentUserId); 
        
        if(!optionalAppointment.isPresent() || !optionalPatient.isPresent() || !optionalMedicalStaff.isPresent()) {
                        throw new NotFoundException("Appointment or User does not exist.");
        } else {
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setDiagnosis(request.getDiagnosis());
            medicalRecord.setTreatmentPlan(request.getTreatmentPlan());
            medicalRecord.setNotes(request.getNotes());
            medicalRecord.setMedicalStaff(optionalMedicalStaff.get().getFirstName()+ " " + optionalMedicalStaff.get().getLastName());
            medicalRecord.setAppointment(optionalAppointment.get());
            medicalRecord.setPatient(optionalPatient.get());
            medicalRecordRepository.save(medicalRecord);
            return 1;
        }
    }*/

    @Override
    public int createMedicalRecord(MedicalRecordRequestDTO request, Long appointmentId, Long patientId, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        try {
            CompletableFuture<Optional<Appointment>> appointmentFuture = getAppointmentAsync(appointmentId);
            CompletableFuture<Optional<Patient>> patientFuture = getPatientAsync(patientId);
            CompletableFuture<Optional<MedicalStaff>> staffFuture = getStaffAsync(currentUserId);

            CompletableFuture.allOf(appointmentFuture, patientFuture, staffFuture).join();

            Optional<Appointment> optionalAppointment = appointmentFuture.get();
            Optional<Patient> optionalPatient = patientFuture.get();
            Optional<MedicalStaff> optionalMedicalStaff = staffFuture.get();

            if(!optionalAppointment.isPresent() || !optionalPatient.isPresent() || !optionalMedicalStaff.isPresent()) {
                throw new NotFoundException("Appointment or User does not exist.");
            }

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setDiagnosis(request.getDiagnosis());
            medicalRecord.setTreatmentPlan(request.getTreatmentPlan());
            medicalRecord.setNotes(request.getNotes());
            medicalRecord.setMedicalStaff(optionalMedicalStaff.get().getFirstName() + " " + optionalMedicalStaff.get().getLastName());
            medicalRecord.setAppointment(optionalAppointment.get());
            medicalRecord.setPatient(optionalPatient.get());

            saveMedicalRecordAsync(medicalRecord);

            return 1;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error processing medical record creation", e);
        }
    }
}

