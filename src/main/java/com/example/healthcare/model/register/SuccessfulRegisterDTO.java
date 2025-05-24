package com.example.healthcare.model.register;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SuccessfulRegisterDTO {

    public SuccessfulRegisterDTO() {
        this.creationTime = LocalDateTime.now();
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime creationTime;


}
