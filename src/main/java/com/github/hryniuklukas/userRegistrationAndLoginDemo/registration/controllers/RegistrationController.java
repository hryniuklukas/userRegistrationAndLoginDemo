package com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.controllers;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.model.RegistrationRequest;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.services.ConfirmationTokenService;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ConfirmationTokenService confirmationTokenService;
    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);

    }
    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
