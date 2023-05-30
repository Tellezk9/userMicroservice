package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.spi.IOwnerPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@AllArgsConstructor
public class OwnerMysqlAdapter implements IOwnerPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveOwner(Owner owner) {
        if (userRepository.findByMail(owner.getMail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        if (userRepository.findByDniNumber(owner.getDniNumber()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        userRepository.save(userEntityMapper.ownerToUserEntity(owner));
    }

    @Override
    public Owner getOwnerById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findByIdAndRoleEntityId(id, Constants.OWNER_ROLE_ID);
        if (!userEntity.isPresent()){
            throw new UserNotFoundException();
        }
        return userEntityMapper.userEntityToOwner(userEntity.get());
    }
}
