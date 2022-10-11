package com.github.hryniuklukas.userRegistrationAndLoginDemo.controllers;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.RegistrationRequest;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;
    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);

    }}
