package com.example.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.healthcare.entity.Billing;

import java.util.List;


@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findAllByPatientId(Long id);

}
