package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserDniAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserMailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.domain.model.Client;
import com.pragma.powerup.usermicroservice.domain.spi.IClientPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class ClientMysqlAdapter implements IClientPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveClient(Client client) {

        if (userRepository.findByDniNumber(client.getDniNumber()).isPresent()) {
            throw new UserDniAlreadyExistsException();
        }

        if (userRepository.findByMail(client.getMail()).isPresent()) {
            throw new UserMailAlreadyExistsException();
        }

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        userRepository.save(userEntityMapper.clientToUserEntity(client));
    }
}
