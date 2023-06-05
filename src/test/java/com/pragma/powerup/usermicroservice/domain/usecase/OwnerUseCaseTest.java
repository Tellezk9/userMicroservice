package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.auth.IAuthUser;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IOwnerPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerUseCaseTest {
    @Mock
    private IOwnerPersistencePort ownerPersistencePort;
    @Mock
    private IAuthUser authUser;
    @InjectMocks
    private OwnerUseCase ownerUseCase;

    @Test
    void saveOwner() {
        Role role = new Role(4L, null, null);
        Owner owner = new Owner(1L,"test", "testLastName", 1234, "+123456789012", "2002/05/01", "test@gmail.com", "1234", role);
        doNothing().when(ownerPersistencePort).saveOwner(owner);
        when(authUser.getRole()).thenReturn(Constants.PROVIDER_ROLE_NAME);
        ownerUseCase.saveOwner(owner);
        verify(ownerPersistencePort, times(1)).saveOwner(owner);
    }

    @Test
    void getOwnerById(){
        Role role = new Role(4L, null, null);
        Owner owner = new Owner(1L,"test", "testLastName", 1234, "+123456789012", "2002/05/01", "test@gmail.com", "1234", role);
        when(ownerPersistencePort.getOwnerById(1L)).thenReturn(owner);

        ownerPersistencePort.getOwnerById(1L);

        verify(ownerPersistencePort, times(1)).getOwnerById(1L);
    }
}