package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;

public interface IClientHandler {
    void saveClient(ClientRequestDto clientRequestDto);
}
