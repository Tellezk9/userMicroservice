package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserDniAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserMailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import com.pragma.powerup.usermicroservice.domain.spi.IEmployeePersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@AllArgsConstructor
public class EmployeeMysqlAdapter implements IEmployeePersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveEmployee(Employee employee) {

        if (userRepository.findByDniNumber(employee.getDniNumber()).isPresent()){
            throw new UserDniAlreadyExistsException();
        }

        if (userRepository.findByMail(employee.getMail()).isPresent()){
            throw new UserMailAlreadyExistsException();
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        userRepository.save(userEntityMapper.employeeToUserEntity(employee));
    }

    @Override
    public Employee findByDni(Integer dniEmployee) {
        Optional<UserEntity> userEntity = userRepository.findByDniNumberAndRoleEntityId(dniEmployee, Constants.EMPLOYEE_ROLE_ID);

        if (!userEntity.isPresent()){
            throw new UserNotFoundException();
        }

        return userEntityMapper.userEntityToEmployee(userEntity.get());
    }
}
