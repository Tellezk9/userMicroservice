package com.pragma.powerup.usermicroservice.configuration.security.jwt;

import com.pragma.powerup.usermicroservice.domain.auth.IAuthUser;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthUser implements IAuthUser {
    public static Long id;
    public static String mail;
    public static String role;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
