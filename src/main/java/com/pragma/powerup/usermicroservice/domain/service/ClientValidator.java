package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.model.Client;

public class ClientValidator {

    public boolean allFieldsFilled(Client client) {
        if (
                (client.getName() == null || client.getName().isEmpty()) ||
                        (client.getLastName() == null || client.getLastName().isEmpty()) ||
                        (client.getDniNumber() == null || client.getDniNumber() <= 0) ||
                        (client.getPhone() == null || client.getPhone().isEmpty()) ||
                        (client.getBirthDate() == null || client.getBirthDate().isBlank()) ||
                        (client.getMail() == null || client.getMail().isEmpty()) ||
                        (client.getPassword() == null || client.getPassword().isEmpty())
        ) {
            throw new EmptyFieldFoundException();
        }
        return true;

    }
}
