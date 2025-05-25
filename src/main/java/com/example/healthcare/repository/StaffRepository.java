package com.example.healthcare.repository;

import com.example.healthcare.entity.MedicalStaff;
import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<MedicalStaff, Long> {

    Optional<MedicalStaff> findByEmail(String email);

    Optional<MedicalStaff> findByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT * FROM staff WHERE department=:departmentName AND first_name=:firstName AND last_name=:lastName LIMIT 1", nativeQuery = true)
    Optional<MedicalStaff> findByDepartmentAndFirstNameAndLastName(String departmentName, String firstName, String lastName);

    @Query(value = "SELECT staff FROM medicalstaff staff WHERE department =: departmentName", nativeQuery = true)
    Optional<MedicalStaff> findByDepartment(String departmentName);

}
