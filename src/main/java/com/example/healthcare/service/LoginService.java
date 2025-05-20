package com.example.healthcare.service;

import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;

public interface LoginService {

    SuccessfulRegisterDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO);

}
