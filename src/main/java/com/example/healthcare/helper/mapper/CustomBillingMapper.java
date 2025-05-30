package com.example.healthcare.helper.mapper;

import com.example.healthcare.entity.Billing;
import com.example.healthcare.model.billing.BillingDTO;

public class CustomBillingMapper {

    public static BillingDTO toBillingDTO(Billing billing) {
        BillingDTO billingDTO = new BillingDTO();
        billingDTO.setBillingDate(billing.getBillingDate());
        billingDTO.setAmount(billing.getAmount());
        billingDTO.setPaymentMethod(billing.getPaymentMethod());
        billingDTO.setPatientName(billing.getPatient().getFirstName() + " " + billing.getPatient().getLastName());
        billingDTO.setInsuranceClaimId(billing.getInsuranceClaimId());
        billingDTO.setAppointmentId(billing.getAppointment().getId());
        return billingDTO;
    }

}
