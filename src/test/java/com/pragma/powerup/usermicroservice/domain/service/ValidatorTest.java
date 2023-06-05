package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        assertTrue(validator.hasRoleValid("Test_Role","Test_Role"));
    }
    @Test
    void hasRoleInvalid() {
        assertThrows(EmptyFieldFoundException.class, () -> validator.hasRoleValid(null,null));
        assertThrows(RoleNotAllowedForThisActionException.class, () -> validator.hasRoleValid("Test_Role","Test_Role_Fail"));
    }

    @Test
    @DisplayName("when the phone is valid")
    void isValidPhone() {
        assertTrue(() -> validator.isValidPhone("+123456789012"));
    }

    @ParameterizedTest
    @CsvSource({
            "+123456789",
            "+1234567890129",
    })
    @DisplayName("when the number has less than 10 or more than 13 digits")
    void isValidPhoneOutOfRange(String phone) {
        assertThrows(InvalidPhoneLengthException.class, () -> validator.isValidPhone(phone));
    }

    @Test
    @DisplayName("when the phone is empty")
    void isValidPhoneEmpty() {
        assertThrows(EmptyFieldFoundException.class, () -> validator.isValidPhone(""));
    }

    @Test
    @DisplayName("When the phone does not have the plus (+) character")
    void isValidPhoneWithoutCharacter() {
        assertThrows(InvalidPhoneException.class, () -> validator.isValidPhone("123456789012"));
    }

    @Test
    void isOlder() {
        assertTrue(() -> validator.isOlder("2002/05/01"));
    }

    @Test
    @DisplayName("when the owner isn't older")
    void isNotOlder() {
        assertThrows(UserIsNotOfLegalAgeException.class, () -> validator.isOlder("2012/05/01"));
    }

    @Test
    @DisplayName("when the date is empty")
    void isOlderEmpty() {
        assertThrows(EmptyFieldFoundException.class, () -> validator.isOlder(""));
    }

    @Test
    void isValidMail() {
        assertTrue(validator.isValidMail("test@gmail.com"));
    }
    @Test
    @DisplayName("When the Mail does not have the @ character or (.)")
    void isNotValidMail(){
        assertThrows(InvalidFormatMailException.class,()-> validator.isValidMail("test@icom"));
        assertThrows(InvalidFormatMailException.class,()-> validator.isValidMail("testi.com"));
        assertThrows(InvalidFormatMailException.class,()-> validator.isValidMail("testicom"));
    }
    @Test
    @DisplayName("when the date is empty")
    void isEmptyMail(){
        assertThrows(EmptyFieldFoundException.class,()-> validator.isValidMail(""));
    }

}