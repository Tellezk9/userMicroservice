package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IOwnerServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.service.OwnerValidator;
import com.pragma.powerup.usermicroservice.domain.spi.IOwnerPersistencePort;

public class OwnerUseCase implements IOwnerServicePort {
    private final IOwnerPersistencePort ownerPersistencePort;
    private final OwnerValidator ownerValidator;

    public OwnerUseCase(IOwnerPersistencePort ownerPersistencePort) {
        this.ownerPersistencePort = ownerPersistencePort;
        this.ownerValidator = new OwnerValidator();
    }

    public void saveOwner(Owner owner) {
        ownerValidator.allFieldsFilled(owner);
        ownerValidator.isValidPhone(owner.getPhone());
        ownerValidator.isOlder(owner.getBirthDate());
        ownerValidator.isValidMail(owner.getMail());
        Role role = new Role(Constants.OWNER_ROLE_ID,null,null);
        owner.setRole(role);
        ownerPersistencePort.saveOwner(owner);
    }

    public Owner getOwnerByDni(Integer dni){
        return ownerPersistencePort.getOwnerByDni(dni);
    }
}
