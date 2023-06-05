package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Client;

public interface IClientServicePort {
    void saveClient(Client client);
}
