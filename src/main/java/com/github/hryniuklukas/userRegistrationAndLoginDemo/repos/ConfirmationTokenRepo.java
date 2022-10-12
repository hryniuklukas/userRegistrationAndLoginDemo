package com.github.hryniuklukas.userRegistrationAndLoginDemo.repos;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.security.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
    }