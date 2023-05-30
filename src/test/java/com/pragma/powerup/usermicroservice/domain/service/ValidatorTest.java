package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForThisActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    @Test
    void isIdValid() {
        assertTrue(validator.isIdValid(1));
    }
    @Test
    void isIdInvalid() {
        assertThrows(EmptyFieldFoundException.class, () -> validator.isIdValid(null));
        assertThrows(EmptyFieldFoundException.class, () -> validator.isIdValid(0));
        assertThrows(EmptyFieldFoundException.class, () -> validator.isIdValid(-1));
    }

    @Test
    void hasRoleValid() {
        assertTrue(validator.hasRoleValid(1L,1L));
    }
    @Test
    void hasRoleInvalid() {
        assertThrows(EmptyFieldFoundException.class, () -> validator.hasRoleValid(0L,0L));
        assertThrows(RoleNotAllowedForThisActionException.class, () -> validator.hasRoleValid(1L,2L));
    }
}