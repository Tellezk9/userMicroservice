package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClientResponseDto {
    private Integer id;
    private String name;
    private String lastName;
    private Integer dniNumber;
    private String phone;
    private String birthDate;
    private String mail;
}
