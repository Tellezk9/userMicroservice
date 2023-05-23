package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OwnerResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOwnerHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOwnerRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IOwnerServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerHandlerImpl implements IOwnerHandler {
    private final IOwnerServicePort ownerServicePort;
    private final IOwnerRequestMapper ownerRequestMapper;

    @Override
    public void saveOwner(OwnerRequestDto ownerRequestDto) {
        ownerServicePort.saveOwner(ownerRequestMapper.toOwner(ownerRequestDto));
    }

    @Override
    public OwnerResponseDto getOwnerByDni(Integer id) {
        return ownerRequestMapper.toResponse(ownerServicePort.getOwnerByDni(id));
    }
}
