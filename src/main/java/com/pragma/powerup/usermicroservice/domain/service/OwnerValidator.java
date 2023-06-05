package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Owner;

public class OwnerValidator {

    public boolean allFieldsFilled(Owner owner) {
        if (
                (owner.getName() == null || owner.getName().isEmpty()) ||
                        (owner.getLastName() == null || owner.getLastName().isEmpty()) ||
                        (owner.getDniNumber() == null || owner.getDniNumber() <= 0) ||
                        (owner.getPhone() == null || owner.getPhone().isEmpty()) ||
                        (owner.getBirthDate() == null || owner.getBirthDate().isBlank()) ||
                        (owner.getMail() == null || owner.getMail().isEmpty()) ||
                        (owner.getPassword() == null || owner.getPassword().isEmpty())
        ) {
            throw new EmptyFieldFoundException();
        }
        return true;

    }

}
