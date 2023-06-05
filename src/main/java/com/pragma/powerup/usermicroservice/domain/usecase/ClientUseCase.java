package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IClientServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Client;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.service.ClientValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IClientPersistencePort;

public class ClientUseCase implements IClientServicePort {
    private final IClientPersistencePort clientPersistencePort;
    private final Validator validator;
    private final ClientValidator clientValidator;

    public ClientUseCase(IClientPersistencePort clientPersistencePort) {
        this.clientPersistencePort = clientPersistencePort;
        this.validator = new Validator();
        this.clientValidator = new ClientValidator();
    }

    public void saveClient(Client client) {
        clientValidator.allFieldsFilled(client);

        validator.isValidPhone(client.getPhone());
        validator.isValidMail(client.getMail());

        Role role = new Role(Constants.CLIENT_ROLE_ID, null, null);
        client.setRole(role);

        clientPersistencePort.saveClient(client);
    }
}
