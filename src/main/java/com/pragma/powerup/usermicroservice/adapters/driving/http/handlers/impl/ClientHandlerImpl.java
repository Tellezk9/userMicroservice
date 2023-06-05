package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IClientRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IClientServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientHandlerImpl implements IClientHandler {
    private final IClientServicePort clientServicePort;
    private final IClientRequestMapper clientRequestMapper;

    @Override
    public void saveClient(ClientRequestDto clientRequestDto) {
        clientServicePort.saveClient(clientRequestMapper.toClient(clientRequestDto));
    }
}
