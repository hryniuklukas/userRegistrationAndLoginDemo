package com.github.hryniuklukas.userRegistrationAndLoginDemo.services;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUser;
import com.github.hryniuklukas.userRegistrationAndLoginDemo.repos.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
    private final AppUserRepo appUserRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }
    public String registerAppUser(AppUser appUser){
        return "";
    }
}
