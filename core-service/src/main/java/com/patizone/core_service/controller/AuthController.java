package com.patizone.core_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.patizone.core_service.request.RequestLogin;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.response.ResponseAuth;
import com.patizone.core_service.service.auth.intf.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseAuth login(@RequestBody RequestLogin requestLogin) throws JsonProcessingException {
        return authService.login(requestLogin);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAuth signUp(@RequestBody RequestSignUp requestSignUp){
        return authService.signUp(requestSignUp);
    }
}
