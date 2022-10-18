package com.github.hryniuklukas.userRegistrationAndLoginDemo.user.services;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.services.ConfirmationTokenService;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.user.model.AppUser;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.model.ConfirmationToken;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.user.repos.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
  private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
  private final AppUserRepo appUserRepo;
  private final ConfirmationTokenService confirmationTokenService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return appUserRepo
        .findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
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
  }
  public void enableAppUser(String email){
    appUserRepo.findByEmail(email).ifPresent(appUser -> appUser.setEnabled(true));
  }
}
