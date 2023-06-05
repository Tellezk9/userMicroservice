package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IEmployeeRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IEmployeeServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeHandlerImplTest {
    @Mock
    private IEmployeeServicePort employeeServicePort;
    @Mock
    private IEmployeeRequestMapper employeeRequestMapper;
    @InjectMocks
    private EmployeeHandlerImpl employeeHandler;

    @Test
    void saveEmployee() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
        Employee employee = new Employee(1L, "testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string", null);

        doNothing().when(employeeServicePort).saveEmployee(employee);
        when(employeeRequestMapper.toEmployee(employeeRequestDto)).thenReturn(employee);

        employeeHandler.saveEmployee(employeeRequestDto);

        verify(employeeServicePort, times(1)).saveEmployee(employee);
        verify(employeeRequestMapper, times(1)).toEmployee(employeeRequestDto);
    }

    @Test
    void findByDni() {
        Employee employee = new Employee(1L, "testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string", null);
        Integer dniNumber = 1;

        when(employeeServicePort.findByDni(dniNumber)).thenReturn(employee);

        employeeHandler.findByDni(dniNumber);

        verify(employeeServicePort, times(1)).findByDni(dniNumber);
        verify(employeeRequestMapper, times(1)).toResponse(employee);
    }
}