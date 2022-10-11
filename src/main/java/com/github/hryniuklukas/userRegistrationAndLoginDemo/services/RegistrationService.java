package com.github.hryniuklukas.userRegistrationAndLoginDemo.services;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUser;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUserRole;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.RegistrationRequest;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.security.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    public String register(RegistrationRequest request){
        if(emailValidator.test(request.getEmail())){
            return appUserService.registerAppUser(
                    new AppUser(
                        request.getName(),
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER,
                        false,
                        true
                    )
            );
        }else{
            return "Email is invalid";

        }    }
}
