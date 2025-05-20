package com.example.healthcare.controller;

import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/staff")
public class StaffController {

    @PostMapping("/create")
    public SuccessfulRegisterDTO createNewStaffMember(@RequestBody RegisterUserRequestDTO registerUserRequestDTO){
        return null;
    }


}
