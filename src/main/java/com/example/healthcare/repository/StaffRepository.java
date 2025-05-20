package com.example.healthcare.repository;

import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<MedicalStaff, Long> {

    SuccessfulRegisterDTO save(RegisterUserRequestDTO registerUserRequestDTO);




}
