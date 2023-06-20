package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    @Mock
    private IRolePersistencePort rolePersistencePort;
    @InjectMocks
    private RoleUseCase roleUseCase;

    @Test
    void getAllRoles() {
        List<Role> role = List.of(new Role(1L,"roleName","roleDescription"));

        when(rolePersistencePort.getAllRoles()).thenReturn(role);

        roleUseCase.getAllRoles();

        verify(rolePersistencePort,times(1)).getAllRoles();
    }
}