package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IEmployeeHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IEmployeeRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IEmployeeServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeHandlerImpl implements IEmployeeHandler {

    private final IEmployeeServicePort employeeServicePort;
    private final IEmployeeRequestMapper employeeRequestMapper;

    @Override
    public void saveEmployee(EmployeeRequestDto employeeRequestDto) {
        employeeServicePort.saveEmployee(employeeRequestMapper.toEmployee(employeeRequestDto));
    }

    @Override
    public EmployeeResponseDto findByDni(Integer dniNumber) {
        return employeeRequestMapper.toResponse(employeeServicePort.findByDni(dniNumber));
    }
}
