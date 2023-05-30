package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForThisActionException;

public class Validator {

    public boolean isIdValid(Integer id) {
        if (id == null || id <= 0) {
            throw new EmptyFieldFoundException();
        }
        return true;
    }


    public boolean hasRoleValid(Long authRole, Long validRole) {
        if ((authRole == null || authRole <= 0 ) || (validRole == null || validRole <= 0)) {
            throw new EmptyFieldFoundException();
        }
        if (!authRole.equals(validRole)) {
            throw new RoleNotAllowedForThisActionException();
        }
        return true;
    }
}
