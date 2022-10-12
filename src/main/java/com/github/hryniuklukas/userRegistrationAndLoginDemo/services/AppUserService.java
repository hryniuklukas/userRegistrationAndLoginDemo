package com.github.hryniuklukas.userRegistrationAndLoginDemo.services;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUser;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.repos.AppUserRepo;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.security.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
  private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
  private final AppUserRepo appUserRepo;
  private final ConfirmationTokenService confirmationTokenService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return appUserRepo
        .findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
  }

  public String registerAppUser(AppUser appUser) {
    boolean userFoundInRepo = appUserRepo.findByEmail(appUser.getEmail()).isPresent();

    if (userFoundInRepo) {
      throw new IllegalStateException("User with that email already registered");
    }
    String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
    appUser.setPassword(encodedPassword);

    appUserRepo.save(appUser);

    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
    confirmationTokenService.saveConfirmationToken(confirmationToken);

    return token;

    //TODO: SEND EMAIL
  }
  public void enableAppUser(String email){
    appUserRepo.findByEmail(email).ifPresent(appUser -> appUser.setEnabled(true));
  }
}
