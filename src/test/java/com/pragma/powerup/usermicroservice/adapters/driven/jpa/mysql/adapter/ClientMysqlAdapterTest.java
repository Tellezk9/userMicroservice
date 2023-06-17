package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserDniAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserMailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.domain.model.Client;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientMysqlAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientMysqlAdapter clientMysqlAdapter;

    @Test
    void saveClient() {
        Role role = new Role(4L, null, null);
        Client client = new Client(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
        RoleEntity roleEntity = new RoleEntity(4L, null, null);
        UserEntity user = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", roleEntity);

        when(userRepository.findByDniNumber(client.getDniNumber())).thenReturn(Optional.empty());
        when(userRepository.findByMail(client.getMail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(client.getPassword())).thenReturn("12345t");
        when(userEntityMapper.clientToUserEntity(client)).thenReturn(user);

        clientMysqlAdapter.saveClient(client);

        verify(userRepository).findByDniNumber(client.getDniNumber());
        verify(userRepository).findByMail(client.getMail());
        verify(passwordEncoder).encode(client.getPassword());
        verify(userEntityMapper).clientToUserEntity(client);
    }

    @Test
    void saveClient_ConflictDni() {
        Role role = new Role(4L, null, null);
        Client client = new Client(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
        RoleEntity roleEntity = new RoleEntity(4L, null, null);
        UserEntity user = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", roleEntity);

        when(userRepository.findByDniNumber(client.getDniNumber())).thenReturn(Optional.of(user));

        assertThrows(UserDniAlreadyExistsException.class,()->clientMysqlAdapter.saveClient(client));

        verify(userRepository).findByDniNumber(client.getDniNumber());
    }

    @Test
    void saveClient_ConflictMail() {
        Role role = new Role(4L, null, null);
        Client client = new Client(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
        RoleEntity roleEntity = new RoleEntity(4L, null, null);
        UserEntity user = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", roleEntity);

        when(userRepository.findByDniNumber(client.getDniNumber())).thenReturn(Optional.empty());
        when(userRepository.findByMail(client.getMail())).thenReturn(Optional.of(user));

        assertThrows(UserMailAlreadyExistsException.class,()->clientMysqlAdapter.saveClient(client));

        verify(userRepository).findByDniNumber(client.getDniNumber());
        verify(userRepository).findByMail(client.getMail());
    }

    @Test
    void getClientById() {
        Long id = 1L;
        Long roleId = 1L;

        Role role = new Role(4L, null, null);
        Client client = new Client(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
        RoleEntity roleEntity = new RoleEntity(4L, null, null);
        UserEntity user = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", roleEntity);

        when(userRepository.findByIdAndRoleEntityId(id, roleId)).thenReturn(Optional.of(user));
        when(userEntityMapper.userEntityToClient(user)).thenReturn(client);

        clientMysqlAdapter.getClientById(id);

        verify(userRepository, times(1)).findByIdAndRoleEntityId(id, roleId);
        verify(userEntityMapper, times(1)).userEntityToClient(user);
    }
    @Test
    void getClientById_empty() {
        Long id = 1L;
        Long roleId = 1L;

        when(userRepository.findByIdAndRoleEntityId(id, roleId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->clientMysqlAdapter.getClientById(id));

        verify(userRepository, times(1)).findByIdAndRoleEntityId(id, roleId);
    }
}