package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.ClientResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IClientRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IClientResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IClientServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientHandlerImplTest {

    @Mock
    private IClientRequestMapper clientRequestMapper;
    @Mock
    private IClientResponseMapper clientResponseMapper;
    @Mock
    private IClientServicePort clientServicePort;
    @InjectMocks
    private ClientHandlerImpl clientHandler;

    @Test
    void saveClient() {
        ClientRequestDto clientRequestDto= new ClientRequestDto("testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
        Client client = new Client(null,"testName","testLastName",1234,"+439094230412","2002/05/01","test@gmail.com","string",null);

        doNothing().when(clientServicePort).saveClient(client);
        when(clientRequestMapper.toClient(clientRequestDto)).thenReturn(client);

        clientHandler.saveClient(clientRequestDto);

        verify(clientServicePort,times(1)).saveClient(client);
        verify(clientRequestMapper, times(1)).toClient(clientRequestDto);
    }

    @Test
    void getClientById() {
        Integer id = 1;
        ClientResponseDto clientResponseDto= new ClientResponseDto(1,"testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com");
        Client client = new Client(null,"testName","testLastName",1234,"+439094230412","2002/05/01","test@gmail.com","string",null);

        when(clientServicePort.getClientById(id)).thenReturn(client);
        when(clientResponseMapper.toClientResponseDto(client)).thenReturn(clientResponseDto);

        clientHandler.getClientById(id);

        verify(clientServicePort,times(1)).getClientById(id);
        verify(clientResponseMapper, times(1)).toClientResponseDto(client);
    }
}