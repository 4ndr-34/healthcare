package com.example.healthcare.model.register;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessfulRegisterDTO {

    public SuccessfulRegisterDTO() {
        this.creationTime = LocalDateTime.now();
    }

    LocalDateTime creationTime;


}
