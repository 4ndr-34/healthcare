package com.example.healthcare.model.billing;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BillingDTO {

    private Double amount;
    private LocalDate billingDate;
    private String insuranceClaimId;
    private String paymentMethod;
    private String patientName;
    private LocalDate appointmentDate;
    private Long appointmentId;

}
