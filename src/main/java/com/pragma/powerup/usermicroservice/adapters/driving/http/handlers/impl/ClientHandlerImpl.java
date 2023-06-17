package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.ClientResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IClientRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IClientResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IClientServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientHandlerImpl implements IClientHandler {
    private final IClientServicePort clientServicePort;
    private final IClientRequestMapper clientRequestMapper;
    private final IClientResponseMapper clientResponseMapper;

    @Override
    public void saveClient(ClientRequestDto clientRequestDto) {
        clientServicePort.saveClient(clientRequestMapper.toClient(clientRequestDto));
    }

    @Override
    public ClientResponseDto getClientById(Integer id) {
        return clientResponseMapper.toClientResponseDto(clientServicePort.getClientById(id));
    }
}
