package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Client client = new Client(1L,"test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
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
}