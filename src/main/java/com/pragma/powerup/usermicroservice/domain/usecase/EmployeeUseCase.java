package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IEmployeeServicePort;
import com.pragma.powerup.usermicroservice.domain.auth.IAuthUser;
import com.pragma.powerup.usermicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.service.EmployeeValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IEmployeePersistencePort;

public class EmployeeUseCase implements IEmployeeServicePort {
    private final IEmployeePersistencePort employeePersistencePort;
    private final EmployeeValidator employeeValidator;
    public final IHttpAdapter httpAdapter;
    private final Validator validator;
    public final IAuthUser authUser;

    public EmployeeUseCase(IEmployeePersistencePort employeePersistencePort, IAuthUser authUser, IHttpAdapter httpAdapter) {
        this.employeePersistencePort = employeePersistencePort;
        this.employeeValidator = new EmployeeValidator();
        this.validator = new Validator();
        this.authUser = authUser;
        this.httpAdapter = httpAdapter;
    }

    @Override
    public void saveEmployee(Employee employee) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        employeeValidator.allFieldsFilled(employee);

        httpAdapter.checkRestaurant(Integer.valueOf(Long.toString(authUser.getId())), authUser.getToken());

        validator.isValidPhone(employee.getPhone());
        validator.isOlder(employee.getBirthDate());
        validator.isValidMail(employee.getMail());

        Role role = new Role(Constants.EMPLOYEE_ROLE_ID, null, null);
        employee.setRole(role);

        employeePersistencePort.saveEmployee(employee);
    }

    public Employee findByDni(Integer dniNumber) {
        return employeePersistencePort.findByDni(dniNumber);
    }
}
