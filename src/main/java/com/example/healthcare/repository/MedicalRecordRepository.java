package com.example.healthcare.repository;
import com.example.healthcare.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{

    List<MedicalRecord> getAllByPatientId(Long patientId);

}
