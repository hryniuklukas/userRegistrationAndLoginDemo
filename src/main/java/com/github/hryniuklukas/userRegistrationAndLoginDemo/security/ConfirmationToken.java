package com.github.hryniuklukas.userRegistrationAndLoginDemo.security;

import com.github.hryniuklukas.userRegistrationAndLoginDemo.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable=false)
    private String token;

    @Column(nullable=false)
    private LocalDateTime createdAt;
    @Column(nullable=false)
    private LocalDateTime expiredAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, AppUser appUser){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.appUser = appUser;
    }

}
