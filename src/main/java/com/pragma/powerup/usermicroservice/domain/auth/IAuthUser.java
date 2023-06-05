package com.pragma.powerup.usermicroservice.domain.auth;

public interface IAuthUser {
    Long getId();
    String getMail();
    String getRole();
    String getToken();
}
