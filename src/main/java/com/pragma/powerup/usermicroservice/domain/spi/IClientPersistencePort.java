package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Client;

public interface IClientPersistencePort {
    void saveClient(Client client);
}
