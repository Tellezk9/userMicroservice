package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IOwnerServicePort;
import com.pragma.powerup.usermicroservice.domain.auth.IAuthUser;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.service.OwnerValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IOwnerPersistencePort;

public class OwnerUseCase implements IOwnerServicePort {
    private final IOwnerPersistencePort ownerPersistencePort;
    private final OwnerValidator ownerValidator;
    private final Validator validator;
    private final IAuthUser authUser;

    public OwnerUseCase(IOwnerPersistencePort ownerPersistencePort, IAuthUser authUser) {
        this.ownerPersistencePort = ownerPersistencePort;
        this.ownerValidator = new OwnerValidator();
        this.validator = new Validator();
        this.authUser = authUser;
    }

    public void saveOwner(Owner owner) {
        validator.hasRoleValid(Long.valueOf(authUser.getRole()),Constants.PROVIDER_ROLE_ID);

        ownerValidator.allFieldsFilled(owner);
        ownerValidator.isValidPhone(owner.getPhone());
        ownerValidator.isOlder(owner.getBirthDate());
        ownerValidator.isValidMail(owner.getMail());
        Role role = new Role(Constants.OWNER_ROLE_ID,null,null);
        owner.setRole(role);
        ownerPersistencePort.saveOwner(owner);
    }

    public Owner getOwnerById(Integer id){
        Long idOwner = Long.valueOf(id);
        return ownerPersistencePort.getOwnerById(idOwner);
    }
}
