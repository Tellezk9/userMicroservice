package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.ClientResponseDto;

public interface IClientHandler {
    void saveClient(ClientRequestDto clientRequestDto);
    ClientResponseDto getClientById(Integer id);
}
