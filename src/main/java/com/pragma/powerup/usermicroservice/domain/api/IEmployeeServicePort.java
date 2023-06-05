package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Employee;

public interface IEmployeeServicePort {
    void saveEmployee(Employee employee);
    Employee findByDni(Integer dniNumber);
}
