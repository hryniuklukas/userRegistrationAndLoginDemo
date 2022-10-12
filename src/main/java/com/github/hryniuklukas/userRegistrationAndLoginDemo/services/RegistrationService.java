package com.github.hryniuklukas.userRegistrationAndLoginDemo.services;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUser;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUserRole;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.RegistrationRequest;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.security.ConfirmationToken;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.security.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
  private final EmailValidator emailValidator;
  private final AppUserService appUserService;
  private final ConfirmationTokenService confirmationTokenService;

  public String register(RegistrationRequest request) {

    if (!emailValidator.test(request.getEmail())) {
      throw new IllegalStateException("Invalid email");
    }
    String token =
        appUserService.registerAppUser(
            new AppUser(
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER));
    //TO DO: SEND EMAIL with activation link
    String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
    return token;
  }

  @Transactional
  public String confirmToken(String token) {
    ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token).get();
    if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
      throw new IllegalStateException("Token already expired");
    }
    if(confirmationToken.getConfirmedAt() != null){
      throw new IllegalStateException("Token already confirmed!");
    }
    confirmationTokenService.setTokenAsConfirmed(confirmationToken);
    appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
    return "Token confirmed successfully";
  }
}
