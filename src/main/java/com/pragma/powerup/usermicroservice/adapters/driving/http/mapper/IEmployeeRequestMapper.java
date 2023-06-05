package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRequestMapper {
    Employee toEmployee(EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto toResponse(Employee employee);
}
