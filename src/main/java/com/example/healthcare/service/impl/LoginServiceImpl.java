package com.example.healthcare.service.impl;

import com.example.healthcare.model.register.RegisterUserRequestDTO;
import com.example.healthcare.model.register.SuccessfulRegisterDTO;
import com.example.healthcare.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Override
    public SuccessfulRegisterDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO) {
        return new SuccessfulRegisterDTO();
    }
}
