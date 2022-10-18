package com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.services;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.repos.ConfirmationTokenRepo;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.model.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepo confirmationTokenRepo;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepo.save(confirmationToken);
    }
    public Optional<ConfirmationToken> findByToken(String token){
        return confirmationTokenRepo.findByToken(token);
    }
    public void setTokenAsConfirmed(ConfirmationToken confirmationToken){
        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }
}
