package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Client;
import com.pragma.powerup.usermicroservice.domain.spi.IClientPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientUseCaseTest {
    @Mock
    private IClientPersistencePort clientPersistencePort;
    @InjectMocks
    private ClientUseCase clientUseCase;

    @Test
    void saveClient() {
        Client client = new Client(null,"testName","testLastName",123,"+53322222222","2000/05/01","test@email.com","string",null);

        doNothing().when(clientPersistencePort).saveClient(client);

        clientUseCase.saveClient(client);

        verify(clientPersistencePort, times(1)).saveClient(client);
    }
}