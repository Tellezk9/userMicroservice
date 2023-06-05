package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeMysqlAdapterTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeMysqlAdapter employeeMysqlAdapter;


    @Test
    void saveEmployee() {
        Role role = new Role(4L, null, null);
        Employee employee = new Employee(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", role);
        RoleEntity roleEntity = new RoleEntity(2L, null, null);
        UserEntity user = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", roleEntity);

        when(userRepository.findByDniNumber(employee.getDniNumber())).thenReturn(Optional.empty());
        when(userRepository.findByMail(employee.getMail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(employee.getPassword())).thenReturn("12345t");
        when(userEntityMapper.employeeToUserEntity(employee)).thenReturn(user);

        employeeMysqlAdapter.saveEmployee(employee);

        verify(userRepository).findByDniNumber(employee.getDniNumber());
        verify(userRepository).findByMail(employee.getMail());
        verify(passwordEncoder).encode(employee.getPassword());
        verify(userEntityMapper).employeeToUserEntity(employee);
    }

    @Test
    void findByDni() {
        UserEntity userEntity = new UserEntity(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", null);
        Employee employee = new Employee(1L, "test", "testLastName", 12345, "+123456789012", "2002/05/01", "test@gmail.com", "12345t", null);
        Integer dniEmployee = 1;

        when(userRepository.findByDniNumberAndRoleEntityId(dniEmployee, Constants.EMPLOYEE_ROLE_ID)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.userEntityToEmployee(userEntity)).thenReturn(employee);

        employeeMysqlAdapter.findByDni(dniEmployee);

        verify(userEntityMapper, times(1)).userEntityToEmployee(userEntity);
        verify(userRepository, times(1)).findByDniNumberAndRoleEntityId(dniEmployee, Constants.EMPLOYEE_ROLE_ID);

    }
}