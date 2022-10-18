package com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.model;


public record RegistrationRequest(String name, String username, String password, String email) {
}
