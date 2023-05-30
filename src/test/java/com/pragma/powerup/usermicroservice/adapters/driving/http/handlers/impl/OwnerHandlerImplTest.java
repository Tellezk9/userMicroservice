package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOwnerRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IOwnerServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerHandlerImplTest {
    @Mock
    private IOwnerServicePort ownerServicePort;
    @Mock
    private IOwnerRequestMapper ownerRequestMapper;
    @InjectMocks
    private OwnerHandlerImpl ownerHandler;

    @Test
    void saveOwner() {
        OwnerRequestDto ownerRequestDto= new OwnerRequestDto("testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
        Owner owner = new Owner(null,"testName","testLastName",1234,"+439094230412","2002/05/01","test@gmail.com","string",null);
        doNothing().when(ownerServicePort).saveOwner(owner);
        when(ownerRequestMapper.toOwner(ownerRequestDto)).thenReturn(owner);

        ownerHandler.saveOwner(ownerRequestDto);

        verify(ownerServicePort,times(1)).saveOwner(owner);
        verify(ownerRequestMapper, times(1)).toOwner(ownerRequestDto);
    }

    @Test
    void getOwnerById() {
        Owner owner = new Owner(null,"testName","testLastName",1234,"+439094230412","2002/05/01","test@gmail.com","string",null);
        when(ownerServicePort.getOwnerById(1)).thenReturn(owner);

        ownerHandler.getOwnerById(1);

        verify(ownerServicePort,times(1)).getOwnerById(1);
        verify(ownerRequestMapper,times(1)).toResponse(owner);
    }
}