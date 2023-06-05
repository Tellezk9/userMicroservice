package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {

    public boolean isIdValid(Integer id) {
        if (id == null || id <= 0) {
            throw new EmptyFieldFoundException();
        }
        return true;
    }


    public boolean hasRoleValid(String authRole, String validRole) {
        if ((authRole == null || authRole.isEmpty()) || (validRole == null || validRole.isEmpty())) {
            throw new EmptyFieldFoundException();
        }
        if (!authRole.equals(validRole)) {
            throw new RoleNotAllowedForThisActionException();
        }
        return true;
    }

    public boolean isValidPhone(String phone) {
        if (phone.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        if (phone.length() > 13) {
            throw new InvalidPhoneLengthException();
        }
        if (phone.length() > 10) {
            char[] charPhone = phone.toCharArray();
            if (charPhone[0] != 43) {
                throw new InvalidPhoneException();
            }
            for (int i = 1; i < charPhone.length; i++) {
                if (charPhone[i] < 48 || charPhone[i] > 57) {
                    throw new InvalidPhoneException();
                }
            }
        }
        if (phone.length() <= 10) {
            throw new InvalidPhoneLengthException();
        }
        return true;
    }

    public boolean isOlder(String date) {
        if (date == null || date.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        LocalDate validDate = LocalDate.now().minusYears(18);
        LocalDate lastDate;

        try {
            lastDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (Exception ex) {
            throw new InvalidFormatDateException();
        }

        if (validDate.isBefore(lastDate)) {
            throw new UserIsNotOfLegalAgeException();
        }
        return true;
    }

    public boolean isValidMail(String mail) {
        if (mail == null || mail.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        if (!(mail.contains("@") && mail.contains("."))) {
            throw new InvalidFormatMailException();
        }
        return true;
    }
}
