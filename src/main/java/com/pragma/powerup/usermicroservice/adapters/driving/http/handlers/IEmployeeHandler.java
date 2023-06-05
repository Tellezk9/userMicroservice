package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;

public interface IEmployeeHandler {
    void saveEmployee(EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto findByDni(Integer dniNumber);
}
