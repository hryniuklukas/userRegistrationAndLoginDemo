package com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.email.services;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s){
        return s.contains("@") && s.substring(s.length() - 4, s.length()).contains(".");
    }
}
