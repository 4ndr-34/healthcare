package com.example.healthcare.security;

import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //check if it's in staff table first
        Optional<MedicalStaff> optionalStaff = staffRepository.findByEmail(username);
        if (optionalStaff.isPresent()) {
            MedicalStaff staff = optionalStaff.get();
            return new CustomUserDetails.Builder()
                    .id(staff.getId())
                    .email(staff.getEmail())
                    .password(staff.getPassword())
                    .userRole(staff.getRole().name())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + staff.getRole().name())))
                    .build();
        }

        //check if it's in patient table
        Optional<Patient> optionalPatient = patientRepository.findByEmail(username);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return new CustomUserDetails.Builder()
                    .id(patient.getId())
                    .email(patient.getEmail())
                    .password(patient.getPassword())
                    .userRole(patient.getRole().name())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+patient.getRole().name())))
                    .build();

        }

        throw new UsernameNotFoundException("User not found with this email: " + username);
    }
}
