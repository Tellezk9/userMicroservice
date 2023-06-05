package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.model.Employee;

public class EmployeeValidator {

    public boolean allFieldsFilled(Employee employee) {
        if (
                (employee.getName() == null || employee.getName().isEmpty()) ||
                        (employee.getLastName() == null || employee.getLastName().isEmpty()) ||
                        (employee.getDniNumber() == null || employee.getDniNumber() <= 0) ||
                        (employee.getPhone() == null || employee.getPhone().isEmpty()) ||
                        (employee.getBirthDate() == null || employee.getBirthDate().isBlank()) ||
                        (employee.getMail() == null || employee.getMail().isEmpty()) ||
                        (employee.getPassword() == null || employee.getPassword().isEmpty())
        ) {
            throw new EmptyFieldFoundException();
        }
        return true;

    }
}
