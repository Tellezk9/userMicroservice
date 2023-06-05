package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class OwnerValidatorTest {

    private OwnerValidator ownerValidator;

    @BeforeEach
    void setUp() {
        ownerValidator = new OwnerValidator();
    }

    @Test
    void allFieldsFilled() {
        Role role = new Role(4L, null, null);
        Owner owner = new Owner(1L,"test", "testLastName", 1234, "+123456789012", "2002/05/01", "test@gmail.com", "1234", role);
        assertTrue(() -> ownerValidator.allFieldsFilled(owner));
    }

    @ParameterizedTest
    @CsvSource({
            ",testLastName, 1234, +123456789012, 2002/05/01, test@gmail.com, 1234",
            "test,, 1234, +123456789012, 2002/05/01, test@gmail.com, 1234",
            "test,testLastName, , +123456789012, 2002/05/01, test@gmail.com, 1234",
            "test,testLastName, 1234, , 2002/05/01, test@gmail.com, 1234",
            "test,testLastName, 1234, +123456789012, , test@gmail.com, 1234",
            "test,testLastName, 1234, +123456789012, 2002/05/01, , 1234",
            "test,testLastName, 1234, +123456789012, 2002/05/01, test@gmail.com, ",
    })
    void isEmptyWithoutParams(String name, String lastName, Integer dniNumber, String phone, String birthDate, String mail, String password) {

        Role role = new Role(4L, null, null);
        Owner owner = new Owner(1L,name, lastName, dniNumber, phone, birthDate, mail, password, role);

        assertThrows(EmptyFieldFoundException.class, () -> ownerValidator.allFieldsFilled(owner));
    }
}