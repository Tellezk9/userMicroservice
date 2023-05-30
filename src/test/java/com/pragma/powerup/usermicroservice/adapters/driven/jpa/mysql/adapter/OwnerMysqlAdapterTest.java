package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerMysqlAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private OwnerMysqlAdapter ownerMysqlAdapter;

    @Test
    void saveOwnerSuccessful() {
        Role role = new Role(4L, null, null);
        Owner owner = new Owner(1L,"test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
        RoleEntity roleEntity = new RoleEntity(4L, null, null);
        UserEntity user = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", roleEntity);

        when(userRepository.findByDniNumber(owner.getDniNumber())).thenReturn(Optional.empty());
        when(userRepository.findByMail(owner.getMail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(owner.getPassword())).thenReturn("12345t");
        when(userEntityMapper.ownerToUserEntity(owner)).thenReturn(user);

        ownerMysqlAdapter.saveOwner(owner);

        verify(userRepository).findByDniNumber(owner.getDniNumber());
        verify(userRepository).findByMail(owner.getMail());
        verify(passwordEncoder).encode(owner.getPassword());
        verify(userEntityMapper).ownerToUserEntity(owner);

    }

    @Test
    void getOwnerByDni(){
        UserEntity userEntity =  new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", null);
        Owner owner = new Owner(1L,"test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", null);

        when(userRepository.findByIdAndRoleEntityId(1L,Constants.OWNER_ROLE_ID)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.userEntityToOwner(userEntity)).thenReturn(owner);

        ownerMysqlAdapter.getOwnerById(1L);

        verify(userEntityMapper, times(1)).userEntityToOwner(userEntity);
        verify(userRepository, times(1)).findByIdAndRoleEntityId(1L,Constants.OWNER_ROLE_ID);
    }
}