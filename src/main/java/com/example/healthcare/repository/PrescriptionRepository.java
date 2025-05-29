package com.example.healthcare.repository;

import com.example.healthcare.entity.Prescription;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findAllByPatientId(Long id);
    
    Optional<Prescription> findByAppointmentId(Long id); 

}
